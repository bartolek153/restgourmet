
package app.restgourmet.api.inventoryhandling.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.EditStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.ProcessStockTakingResultDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingItemDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;
import app.restgourmet.api.inventoryhandling.enums.StockTakingItemStatus;
import app.restgourmet.api.inventoryhandling.enums.StockTakingStatus;
import app.restgourmet.api.inventoryhandling.exceptions.QuantityExceededException;
import app.restgourmet.api.inventoryhandling.mappers.StockTakingMapper;
import app.restgourmet.api.inventoryhandling.models.StockTaking;
import app.restgourmet.api.inventoryhandling.models.StockTakingItem;
import app.restgourmet.api.inventoryhandling.repository.StockTakingItemRepository;
import app.restgourmet.api.inventoryhandling.repository.StockTakingRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.StockTakingSpecification;
import app.restgourmet.api.inventoryhandling.service.spec.CurrentStockService;
import app.restgourmet.api.inventoryhandling.service.spec.StockTakingService;
import app.restgourmet.api.masterdata.repository.ProductRepository;
import app.restgourmet.api.masterdata.repository.WarehouseRepository;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;
import jakarta.transaction.Transactional;

@Service
public class StockTakingServiceImpl implements StockTakingService {

  private final CurrentStockService currentStockService;
  private final StockTakingRepository stockTakingRepository;
  private final StockTakingItemRepository stockTakingItemRepository;
  private final UserRepository userRepository;
  private final WarehouseRepository warehouseRepository;
  private final ProductRepository productRepository;

  @Autowired
  private StockTakingMapper stockTakingMapper;

  public StockTakingServiceImpl(
      StockTakingRepository stockTakingRepository,
      StockTakingItemRepository stockTakingItemRepository,
      UserRepository userRepository,
      WarehouseRepository warehouseRepository,
      ProductRepository productRepository,
      CurrentStockService currentStockService) {
    this.currentStockService = currentStockService;
    this.stockTakingRepository = stockTakingRepository;
    this.stockTakingItemRepository = stockTakingItemRepository;
    this.userRepository = userRepository;
    this.warehouseRepository = warehouseRepository;
    this.productRepository = productRepository;
  }

  public PagedModel<StockTakingListDto> list(PageRequest pageReq, StockTakingListFiltersDto filters) {
    Specification<StockTaking> spec = StockTakingSpecification.filterBy(filters);
    Page<StockTakingListDto> res = stockTakingRepository.findAll(spec, pageReq)
        .map(stockTakingMapper::toListDto);

    return new PagedModel<>(res);
  }

  public StockTakingDto getOne(UUID id) {
    StockTaking st = getById(id); // TODO: if line status is pending, show current stock qty
    return stockTakingMapper.toDto(st);
  }

  public List<StockTakingItemDto> getItems(UUID id) {
    List<StockTakingItem> items = stockTakingItemRepository.findByStockTakingId(id);
    return items
        .stream()
        .map(stockTakingMapper::toItemDtoList)
        .toList();
  }

  @Transactional
  public UUID create(CreateStockTakingDto dto, UUID userId) {
    StockTaking st = stockTakingMapper.createDtoToEntity(dto);

    if (!warehouseRepository.existsById(dto.getWarehouseId())) {
      throw new ResourceNotFoundException(ErrorMessages.WAREHOUSE_NOT_FOUND);
    }

    for (StockTakingItemDto item : dto.getItems()) {
      if (!productRepository.existsById(item.getProductId())) {
        throw new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND);
      }

      StockTakingItem sti = new StockTakingItem();
      sti.setProduct(productRepository.getReferenceById(item.getProductId()));
      sti.setStatus(StockTakingItemStatus.PENDING);
      sti.setStockTaking(st);

      st.addItem(sti);
    }

    st.setWarehouse(warehouseRepository.getReferenceById(dto.getWarehouseId()));
    st.setCreatedBy(userRepository.getReferenceById(userId));
    st.setStartDate(LocalDateTime.now());
    st.setStatus(StockTakingStatus.OPEN);

    return stockTakingRepository.save(st).getId();
  }

  @Transactional
  public void edit(UUID id, EditStockTakingDto dto) {

    StockTaking st = getById(id);
    stockTakingMapper.updateEntityHeader(dto, st);

    Set<StockTakingItem> children = new HashSet<>();

    if (!warehouseRepository.existsById(dto.getWarehouseId())) {
      throw new ResourceNotFoundException(ErrorMessages.WAREHOUSE_NOT_FOUND);
    }

    st.setWarehouse(warehouseRepository.getReferenceById(dto.getWarehouseId()));

    for (StockTakingItemDto itemDto : dto.getItems()) {
      StockTakingItem itemEnt;

      if (itemDto.getId() == null) {
        // CREATE MODE: create new child
        if (st.getStatus() != StockTakingStatus.OPEN) {
          throw new BadRequestException(ErrorMessages.STOCK_TAKING_ADD_NOT_OPEN);
        }

        itemEnt = new StockTakingItem();
        stockTakingMapper.updateEntityItem(itemDto, itemEnt);
        itemEnt.setStockTaking(st);
        itemEnt.setStatus(StockTakingItemStatus.PENDING);
      } else {
        // UPDATE MODE : fetch by id
        itemEnt = getItemById(itemDto.getId());
        if (itemEnt.getStatus() == StockTakingItemStatus.PROCESSED) {
          children.add(itemEnt);
          continue;
        }
        
        stockTakingMapper.updateEntityItem(itemDto, itemEnt);
      }

      if (!productRepository.existsById(itemDto.getProductId())) {
        throw new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND);
      }

      itemEnt.setProduct(productRepository.getReferenceById(itemDto.getProductId()));

      children.add(itemEnt);
    }
    st.getItems().clear();
    st.getItems().addAll(children);
    stockTakingRepository.save(st);
  }

  @Transactional
  public void delete(UUID id) {
    StockTaking st = getById(id);

    if (st.getStatus() == StockTakingStatus.CLOSED) {
      throw new BadRequestException(ErrorMessages.STOCK_TAKING_DELETE_ALREADY_CLOSED);
    }

    stockTakingRepository.delete(st);
  }

  @Transactional
  public ProcessStockTakingResultDto process(UUID id) {
    StockTaking st = getById(id);
    boolean hasErrors = false;
    boolean partialProc = false;

    if (st.getStatus() == StockTakingStatus.CLOSED) {
      throw new BadRequestException(ErrorMessages.STOCK_TAKING_PROCESS_CLOSED);
    }

    for (StockTakingItem item : stockTakingItemRepository.findByStockTakingIdAndStatus(
        st.getId(),
        StockTakingItemStatus.PENDING)) {
      try {
        if (item.getCountedQuantity() == null) {
          partialProc = true;
          continue;
        }

        currentStockService.adjustStock(
            st.getWarehouse(),
            item.getProduct(),
            item.getCountedQuantity());

        item.setError(false);
        item.setMessage(null);
        item.setStatus(StockTakingItemStatus.PROCESSED);

        // TODO: salvar estoque atual em systemQuantity antes de processar

        // TODO: create stock transaction

      } catch (QuantityExceededException e) {
        if (!hasErrors)
          hasErrors = true;

        item.setError(true);
        item.setMessage(e.getMessage());
      } catch (Exception e) {
        if (!hasErrors)
          hasErrors = true;

        item.setError(true);
        item.setMessage(
            String.format(ErrorMessages.SHARED_ITEM_HAS_ERRORS, e.getMessage()));
      }
    }

    ProcessStockTakingResultDto res = new ProcessStockTakingResultDto();

    
    if (hasErrors) {
      st.setStatus(StockTakingStatus.PARTIALLY_PROCESSED);
      stockTakingRepository.save(st);
      res.setMessage(ErrorMessages.STOCK_TAKING_PROCESS_ERROR);
      res.setSuccess(false);
    } else {
      res.setSuccess(true);

      if (partialProc) {
        st.setStatus(StockTakingStatus.PARTIALLY_PROCESSED);
        res.setMessage(ErrorMessages.STOCK_TAKING_PROCESS_SUCCESS_PARTIAL);
      }
      else {
        st.setStatus(StockTakingStatus.CLOSED);
        st.setEndDate(LocalDateTime.now());
        res.setMessage(ErrorMessages.STOCK_TAKING_PROCESS_SUCCESS);
      }
    }

    return (res);
  }

  private StockTaking getById(UUID id) {
    return stockTakingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.STOCK_TAKING_NOT_FOUND));
  }

  private StockTakingItem getItemById(UUID id) {
    return stockTakingItemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.STOCK_TAKING_ITEM_NOT_FOUND));
  }
}