package app.restgourmet.api.inventoryhandling.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.stock.InventoryDto;
import app.restgourmet.api.inventoryhandling.dto.stock.InventoryListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.InventoryListFiltersDto;
import app.restgourmet.api.inventoryhandling.mappers.CurrentStockMapper;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import app.restgourmet.api.inventoryhandling.repository.CurrentStockRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.CurrentStockSpecification;
import app.restgourmet.api.inventoryhandling.service.spec.CurrentStockService;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.masterdata.repository.UnitMeasurementRepository;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@Service
public class CurrentStockServiceImpl implements CurrentStockService {

  private final CurrentStockRepository currentStockRepository;
  private final UnitMeasurementRepository unitMeasurementRepository;

  @Autowired
  private CurrentStockMapper inventoryMapper;

  public CurrentStockServiceImpl(
      CurrentStockRepository inventoryRepository,
      UnitMeasurementRepository unitMeasurementRepository) {
    this.currentStockRepository = inventoryRepository;
    this.unitMeasurementRepository = unitMeasurementRepository;
  }

  public PagedModel<InventoryListDto> list(PageRequest pagReq, InventoryListFiltersDto filters) {
    Specification<CurrentStock> spec = CurrentStockSpecification.filterBy(filters);
    Page<CurrentStock> pg = currentStockRepository.findAll(spec, pagReq);
    return new PagedModel<>(pg.map(inventoryMapper::toListDto));
  }

  public InventoryDto getOne(UUID id) {
    return inventoryMapper.toDto(getById(id));
  }

  public boolean checkStockAvailability(Product prod, Double qty) {
    return currentStockRepository.sumQuantityByProductId(prod.getId()) > qty;
  }

  public boolean checkStockAvailability(Warehouse wh, Product prod, Double qty) {
    CurrentStock inv = getByWarehouseProduct(wh, prod);
    return inv.getQty() > qty;
  }

  public boolean checkStockAvailability(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    if (prod.getInventoryUnit().getBaseUnit() != unit.getBaseUnit()) {
      throw new BadRequestException(ErrorMessages.UNIT_MEASUREMENT_BAD_CONVERSION);
    }
    return checkStockAvailability(wh, prod, qty * unit.getConversionFactor());
  }

  public void increaseStock(Warehouse wh, Product prod, Double qty) {
    CurrentStock inv = getByWarehouseProduct(wh, prod);
    inv.increaseQuantity(qty);

    // validate max qty rules
    if (inv.getMaxQty() > 0 && inv.quantityExceeded()) {
      throw new BadRequestException(ErrorMessages.INVENTORY_QUANTITY_EXCEEDED);
    }

    currentStockRepository.save(inv);
  }

  public void increaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    increaseStock(wh, prod, qty * unit.getConversionFactor());
  }

  public void decreaseStock(Warehouse wh, Product prod, Double qty) {
    CurrentStock inv = getByWarehouseProduct(wh, prod);
    inv.decreaseQuantity(qty);

    if (inv.insufficientStock()) {
      throw new BadRequestException(ErrorMessages.INVENTORY_INSUFFICIENT);
    }

    currentStockRepository.save(inv);
  }

  public void decreaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    decreaseStock(wh, prod, qty * unit.getConversionFactor());
  }

  private CurrentStock getById(UUID id) {
    return currentStockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.INVENTORY_NOT_FOUND));
  }

  private CurrentStock getByWarehouseProduct(Warehouse wh, Product prod) {
    return currentStockRepository.findByWarehouseAndProduct(wh, prod)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.INVENTORY_UNAVAILABLE));
  }

  @Override
  public void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'moveStock'");
  }

  @Override
  public void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty, UnitMeasurement unit) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'moveStock'");
  }
}
