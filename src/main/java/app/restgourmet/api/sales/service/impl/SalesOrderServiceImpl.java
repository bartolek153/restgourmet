package app.restgourmet.api.sales.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.inventoryhandling.enums.TransactionType;
import app.restgourmet.api.inventoryhandling.models.InventoryLog;
import app.restgourmet.api.inventoryhandling.models.ProductInventory;
import app.restgourmet.api.masterdata.mappers.AddressMapper;
import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.masterdata.repository.AddressRepository;
import app.restgourmet.api.masterdata.repository.ProductRepository;
import app.restgourmet.api.masterdata.repository.WarehouseRepository;
import app.restgourmet.api.sales.dto.SalesOrderDto;
import app.restgourmet.api.sales.dto.SalesOrderItemDto;
import app.restgourmet.api.sales.dto.SalesOrderListDto;
import app.restgourmet.api.sales.dto.SalesOrderListFiltersDto;
import app.restgourmet.api.sales.enums.OrderStatus;
import app.restgourmet.api.sales.mappers.SalesOrderMapper;
import app.restgourmet.api.sales.models.Customer;
import app.restgourmet.api.sales.models.SalesOrder;
import app.restgourmet.api.sales.models.SalesOrderItem;
import app.restgourmet.api.sales.repository.CustomerRepository;
import app.restgourmet.api.sales.repository.SalesOrderRepository;
import app.restgourmet.api.sales.service.spec.SalesOrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

  private final SalesOrderRepository salesOrderRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;
  private final WarehouseRepository warehouseRepository;
  private final AddressRepository addressRepository;
  private final SalesOrderMapper salesOrderMapper;
  private final AddressMapper addressMapper;

  @PersistenceContext
  private EntityManager entityManager;

  public SalesOrderServiceImpl(
      SalesOrderRepository salesOrderRepository,
      CustomerRepository customerRepository,
      ProductRepository productRepository,
      WarehouseRepository warehouseRepository,
      AddressRepository addressRepository,
      SalesOrderMapper salesOrderMapper,
      AddressMapper addressMapper) {
    this.salesOrderRepository = salesOrderRepository;
    this.customerRepository = customerRepository;
    this.productRepository = productRepository;
    this.warehouseRepository = warehouseRepository;
    this.addressRepository = addressRepository;
    this.salesOrderMapper = salesOrderMapper;
    this.addressMapper = addressMapper;
  }

  @Override
  public PagedModel<SalesOrderListDto> list(PageRequest pageReq, SalesOrderListFiltersDto filters) {
    Specification<SalesOrder> spec = createSpecification(filters);
    Page<SalesOrder> page = salesOrderRepository.findAll(spec, pageReq);
    Page<SalesOrderListDto> res = page.map(salesOrderMapper::toListDto);
    return new PagedModel<>(res);
  }

  @Override
  public SalesOrderDto getOne(UUID id) {
    SalesOrder salesOrder = getSalesOrderById(id);
    return salesOrderMapper.toDto(salesOrder);
  }

  @Override
  @Transactional
  public UUID create(SalesOrderDto dto) {
    // Validate customer
    Customer customer = customerRepository.findById(dto.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

    // Validate warehouse
    Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
        .orElseThrow(
            () -> new ResourceNotFoundException("Warehouse not found with id: " + dto.getWarehouseId()));
    
    // Handle delivery address
    Address deliveryAddress = null;
    if (dto.getDeliveryAddress() != null) {
        deliveryAddress = addressMapper.toEntity(dto.getDeliveryAddress());
        deliveryAddress = addressRepository.save(deliveryAddress);
    } else if (dto.getDeliveryAddressId() != null) {
        deliveryAddress = addressRepository.findById(dto.getDeliveryAddressId())
            .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + dto.getDeliveryAddressId()));
    }

    // Create sales order
    SalesOrder salesOrder = new SalesOrder();
    salesOrder.setCustomer(customer);
    salesOrder.setWarehouse(warehouse);
    salesOrder.setDeliveryAddress(deliveryAddress);
    salesOrder.setDeliveryDate(dto.getDeliveryDate());
    salesOrder.setPaymentMethod(dto.getPaymentMethod());
    salesOrder.setOrderDate(LocalDate.now());
    salesOrder.setStatus(OrderStatus.PENDING);
    salesOrder.setOrderNumber(generateOrderNumber());

    // Calculate totals
    double subtotal = 0.0;
    double taxAmount = 0.0;

    // Create and add items
    List<SalesOrderItem> items = new ArrayList<>();
    for (SalesOrderItemDto itemDto : dto.getItems()) {
      Product product = productRepository.findById(itemDto.getProductId())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Product not found with id: " + itemDto.getProductId()));

      // Check inventory availability
      checkInventoryAvailability(product, warehouse, itemDto.getQuantity());

      SalesOrderItem item = new SalesOrderItem();
      item.setSalesOrder(salesOrder);
      item.setProduct(product);
      item.setQuantity(itemDto.getQuantity());
      item.setUnitPrice(itemDto.getUnitPrice());
      item.setTaxRate(itemDto.getTaxRate());

      // Calculate item totals
      double itemTotal = itemDto.getQuantity() * itemDto.getUnitPrice();
      double itemTax = itemTotal * (itemDto.getTaxRate() / 100);

      item.setTotalPrice(itemTotal);
      item.setTaxAmount(itemTax);
      item.setAllocated(false);

      subtotal += itemTotal;
      taxAmount += itemTax;

      items.add(item);
    }

    salesOrder.setSubtotal(subtotal);
    salesOrder.setTaxAmount(taxAmount);
    salesOrder.setTotalAmount(subtotal + taxAmount);
    salesOrder.setItems(items);

    // Save the order
    salesOrder = salesOrderRepository.save(salesOrder);

    // Allocate inventory
    allocateInventory(salesOrder);

    return salesOrder.getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, SalesOrderDto dto) {
    SalesOrder salesOrder = getSalesOrderById(id);

    // Only allow editing if the order is in PENDING status
    if (salesOrder.getStatus() != OrderStatus.PENDING) {
      throw new BadRequestException("Cannot edit order that is not in PENDING status");
    }

    // Update customer if changed
    if (!salesOrder.getCustomer().getId().equals(dto.getCustomerId())) {
      Customer customer = customerRepository.findById(dto.getCustomerId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));
      salesOrder.setCustomer(customer);
    }

    // Update warehouse if changed
    if (!salesOrder.getWarehouse().getId().equals(dto.getWarehouseId())) {
      Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Warehouse not found with id: " + dto.getWarehouseId()));
      salesOrder.setWarehouse(warehouse);
    }
    
    // Handle delivery address
    if (dto.getDeliveryAddress() != null) {
        Address deliveryAddress;
        if (salesOrder.getDeliveryAddress() != null) {
            // Update existing address
            deliveryAddress = salesOrder.getDeliveryAddress();
            addressMapper.updateEntity(dto.getDeliveryAddress(), deliveryAddress);
            deliveryAddress = addressRepository.save(deliveryAddress);
        } else {
            // Create new address
            deliveryAddress = addressMapper.toEntity(dto.getDeliveryAddress());
            deliveryAddress = addressRepository.save(deliveryAddress);
        }
        salesOrder.setDeliveryAddress(deliveryAddress);
    } else if (dto.getDeliveryAddressId() != null) {
        // Link to existing address
        Address deliveryAddress = addressRepository.findById(dto.getDeliveryAddressId())
            .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + dto.getDeliveryAddressId()));
        salesOrder.setDeliveryAddress(deliveryAddress);
    }

    // Update other fields
    salesOrder.setDeliveryDate(dto.getDeliveryDate());
    salesOrder.setPaymentMethod(dto.getPaymentMethod());

    // Deallocate current inventory
    deallocateInventory(salesOrder);

    // Clear existing items
    salesOrder.getItems().clear();

    // Calculate new totals
    double subtotal = 0.0;
    double taxAmount = 0.0;

    // Create and add new items
    for (SalesOrderItemDto itemDto : dto.getItems()) {
      Product product = productRepository.findById(itemDto.getProductId())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Product not found with id: " + itemDto.getProductId()));

      // Check inventory availability
      checkInventoryAvailability(product, salesOrder.getWarehouse(), itemDto.getQuantity());

      SalesOrderItem item = new SalesOrderItem();
      item.setSalesOrder(salesOrder);
      item.setProduct(product);
      item.setQuantity(itemDto.getQuantity());
      item.setUnitPrice(itemDto.getUnitPrice());
      item.setTaxRate(itemDto.getTaxRate());

      // Calculate item totals
      double itemTotal = itemDto.getQuantity() * itemDto.getUnitPrice();
      double itemTax = itemTotal * (itemDto.getTaxRate() / 100);

      item.setTotalPrice(itemTotal);
      item.setTaxAmount(itemTax);
      item.setAllocated(false);

      subtotal += itemTotal;
      taxAmount += itemTax;

      salesOrder.getItems().add(item);
    }

    salesOrder.setSubtotal(subtotal);
    salesOrder.setTaxAmount(taxAmount);
    salesOrder.setTotalAmount(subtotal + taxAmount);

    // Save the updated order
    salesOrderRepository.save(salesOrder);

    // Allocate inventory for the updated items
    allocateInventory(salesOrder);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    SalesOrder salesOrder = getSalesOrderById(id);

    // Only allow deletion if the order is in PENDING status
    if (salesOrder.getStatus() != OrderStatus.PENDING) {
      throw new BadRequestException("Cannot delete order that is not in PENDING status");
    }

    // Deallocate inventory
    deallocateInventory(salesOrder);

    // Delete the order
    salesOrderRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void updateStatus(UUID id, OrderStatus status) {
    SalesOrder salesOrder = getSalesOrderById(id);

    // Validate status transition
    validateStatusTransition(salesOrder.getStatus(), status);

    // Handle inventory based on status change
    if (status == OrderStatus.APPROVED) {
      // Decrease inventory and create transaction logs
      decreaseInventory(salesOrder);
    } else if (status == OrderStatus.CANCELLED && salesOrder.getStatus() == OrderStatus.APPROVED) {
      // Restore inventory if cancelling an approved order
      restoreInventory(salesOrder);
    }

    // Update status
    salesOrder.setStatus(status);
    salesOrderRepository.save(salesOrder);
  }

  private SalesOrder getSalesOrderById(UUID id) {
    return salesOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + id));
  }

  private String generateOrderNumber() {
    // Generate a unique order number (e.g., SO-yyyyMMdd-randomDigits)
    String prefix = "SO-" + LocalDate.now().toString().replace("-", "");
    String randomPart = String.format("%04d", (int) (Math.random() * 10000));
    String orderNumber = prefix + "-" + randomPart;

    // Ensure it's unique
    while (salesOrderRepository.existsByOrderNumber(orderNumber)) {
      randomPart = String.format("%04d", (int) (Math.random() * 10000));
      orderNumber = prefix + "-" + randomPart;
    }

    return orderNumber;
  }

  private void checkInventoryAvailability(Product product, Warehouse warehouse, double quantity) {
    // Query to find the product inventory for the given product and warehouse
    String jpql = "SELECT pi FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId";
    List<ProductInventory> inventories = entityManager.createQuery(jpql, ProductInventory.class)
        .setParameter("productId", product.getId())
        .setParameter("warehouseId", warehouse.getId())
        .getResultList();

    if (inventories.isEmpty()) {
      throw new BadRequestException("No inventory found for product: " + product.getDescription()
          + " in warehouse: " + warehouse.getName());
    }

    ProductInventory inventory = inventories.get(0);
    if (inventory.getQuantity() < quantity) {
      throw new BadRequestException("Insufficient inventory for product: " + product.getDescription() +
          ". Available: " + inventory.getQuantity() + ", Requested: " + quantity);
    }
  }

  private void allocateInventory(SalesOrder salesOrder) {
    // Mark items as allocated
    for (SalesOrderItem item : salesOrder.getItems()) {
      item.setAllocated(true);
    }
  }

  private void deallocateInventory(SalesOrder salesOrder) {
    // Mark items as not allocated
    for (SalesOrderItem item : salesOrder.getItems()) {
      item.setAllocated(false);
    }
  }

  private void decreaseInventory(SalesOrder salesOrder) {
    for (SalesOrderItem item : salesOrder.getItems()) {
      // Query to find the product inventory
      String jpql = "SELECT pi FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId";
      List<ProductInventory> inventories = entityManager.createQuery(jpql, ProductInventory.class)
          .setParameter("productId", item.getProduct().getId())
          .setParameter("warehouseId", salesOrder.getWarehouse().getId())
          .getResultList();

      if (!inventories.isEmpty()) {
        ProductInventory inventory = inventories.get(0);

        // Decrease inventory
        inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
        entityManager.merge(inventory);

        // Create inventory transaction log
        InventoryLog log = new InventoryLog();
        log.setProduct(item.getProduct());
        log.setWarehouse(salesOrder.getWarehouse());
        log.setType(TransactionType.OUT);
        log.setQuantity(item.getQuantity());
        log.setTransactionDate(LocalDate.now().atStartOfDay());
        log.setSource("Sales Order");
        log.setReference(salesOrder.getId());
        entityManager.persist(log);
      }
    }
  }

  private void restoreInventory(SalesOrder salesOrder) {
    for (SalesOrderItem item : salesOrder.getItems()) {
      // Query to find the product inventory
      String jpql = "SELECT pi FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId";
      List<ProductInventory> inventories = entityManager.createQuery(jpql, ProductInventory.class)
          .setParameter("productId", item.getProduct().getId())
          .setParameter("warehouseId", salesOrder.getWarehouse().getId())
          .getResultList();

      if (!inventories.isEmpty()) {
        ProductInventory inventory = inventories.get(0);

        // Increase inventory
        inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
        entityManager.merge(inventory);

        // Create inventory transaction log
        InventoryLog log = new InventoryLog();
        log.setProduct(item.getProduct());
        log.setWarehouse(salesOrder.getWarehouse());
        log.setType(TransactionType.IN);
        log.setQuantity(item.getQuantity());
        log.setTransactionDate(LocalDate.now().atStartOfDay());
        log.setSource("Sales Order Cancelled");
        log.setReference(salesOrder.getId());
        entityManager.persist(log);
      }
    }
  }

  private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
    // Define valid status transitions
    if (currentStatus == OrderStatus.PENDING) {
      if (newStatus != OrderStatus.APPROVED && newStatus != OrderStatus.CANCELLED) {
        throw new BadRequestException("Invalid status transition from PENDING to " + newStatus);
      }
    } else if (currentStatus == OrderStatus.APPROVED) {
      if (newStatus != OrderStatus.PROCESSING && newStatus != OrderStatus.CANCELLED) {
        throw new BadRequestException("Invalid status transition from APPROVED to " + newStatus);
      }
    } else if (currentStatus == OrderStatus.PROCESSING) {
      if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
        throw new BadRequestException("Invalid status transition from PROCESSING to " + newStatus);
      }
    } else if (currentStatus == OrderStatus.SHIPPED) {
      if (newStatus != OrderStatus.DELIVERED && newStatus != OrderStatus.CANCELLED) {
        throw new BadRequestException("Invalid status transition from SHIPPED to " + newStatus);
      }
    } else if (currentStatus == OrderStatus.DELIVERED || currentStatus == OrderStatus.CANCELLED) {
      throw new BadRequestException("Cannot change status of an order that is " + currentStatus);
    }
  }

  private Specification<SalesOrder> createSpecification(SalesOrderListFiltersDto filters) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filters.getOrderNumber() != null && !filters.getOrderNumber().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("orderNumber")),
            "%" + filters.getOrderNumber().toLowerCase() + "%"));
      }

      if (filters.getCustomerId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), filters.getCustomerId()));
      }

      if (filters.getCustomerName() != null && !filters.getCustomerName().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("customer").get("name")),
            "%" + filters.getCustomerName().toLowerCase() + "%"));
      }

      if (filters.getStatus() != null) {
        predicates.add(criteriaBuilder.equal(root.get("status"), filters.getStatus()));
      }

      if (filters.getPaymentMethod() != null) {
        predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), filters.getPaymentMethod()));
      }

      if (filters.getOrderDateFrom() != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), filters.getOrderDateFrom()));
      }

      if (filters.getOrderDateTo() != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), filters.getOrderDateTo()));
      }

      if (filters.getDeliveryDateFrom() != null) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("deliveryDate"), filters.getDeliveryDateFrom()));
      }

      if (filters.getDeliveryDateTo() != null) {
        predicates
            .add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryDate"), filters.getDeliveryDateTo()));
      }

      if (filters.getWarehouseId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("warehouse").get("id"), filters.getWarehouseId()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
