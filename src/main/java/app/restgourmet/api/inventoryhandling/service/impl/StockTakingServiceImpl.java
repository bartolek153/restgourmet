
package app.restgourmet.api.inventoryhandling.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingItemDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;
import app.restgourmet.api.inventoryhandling.enums.StockTakingStatus;
import app.restgourmet.api.inventoryhandling.mappers.StockTakingMapper;
import app.restgourmet.api.inventoryhandling.models.StockTaking;
import app.restgourmet.api.inventoryhandling.models.StockTakingItem;
import app.restgourmet.api.inventoryhandling.repository.StockTakingRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.StockTakingSpecification;
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

  private final StockTakingRepository stockTakingRepository;
  private final UserRepository userRepository;
  private final WarehouseRepository warehouseRepository;
  private final ProductRepository productRepository;

  @Autowired
  private StockTakingMapper stockTakingMapper;

  public StockTakingServiceImpl(
      StockTakingRepository stockTakingRepository,
      UserRepository userRepository,
      WarehouseRepository warehouseRepository,
      ProductRepository productRepository) {
    this.stockTakingRepository = stockTakingRepository;
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
    StockTaking st = getById(id);
    return stockTakingMapper.toDto(st);
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
  public void edit(UUID id, StockTakingDto dto) {
    
  }

  @Transactional
  public void delete(UUID id) {
    StockTaking st = getById(id);

    if (st.getStatus() == StockTakingStatus.CLOSED) {
      throw new BadRequestException(ErrorMessages.STOCK_TAKING_ALREADY_CLOSED);
    }

    stockTakingRepository.delete(st);
  }

  private StockTaking getById(UUID id) {
    return stockTakingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.STOCK_TAKING_NOT_FOUND));
  }
}