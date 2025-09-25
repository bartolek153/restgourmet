package app.restgourmet.api.inventoryhandling.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import app.restgourmet.api.commondata.models.BaseUnit;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.stock.EditCurrentStockDto;
import app.restgourmet.api.inventoryhandling.exceptions.QuantityExceededException;
import app.restgourmet.api.inventoryhandling.mappers.CurrentStockMapper;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import app.restgourmet.api.inventoryhandling.repository.CurrentStockRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.CurrentStockSpecification;
import app.restgourmet.api.inventoryhandling.service.spec.CurrentStockService;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.masterdata.repository.UnitMeasurementRepository;
import app.restgourmet.api.shared.exceptions.AppValidationException;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@Service
public class CurrentStockServiceImpl implements CurrentStockService {

  private final CurrentStockRepository currentStockRepository;
  private final UnitMeasurementRepository unitMeasurementRepository;

  @Autowired
  private CurrentStockMapper currentStockMapper;

  public CurrentStockServiceImpl(
      CurrentStockRepository currentStockRepository,
      UnitMeasurementRepository unitMeasurementRepository) {
    this.currentStockRepository = currentStockRepository;
    this.unitMeasurementRepository = unitMeasurementRepository;
  }

  public PagedModel<CurrentStockListDto> list(PageRequest pagReq, CurrentStockListFiltersDto filters) {
    Specification<CurrentStock> spec = CurrentStockSpecification.filterBy(filters);
    Page<CurrentStock> pg = currentStockRepository.findAll(spec, pagReq);
    return new PagedModel<>(pg.map(currentStockMapper::toListDto));
  }

  public CurrentStockDto getOne(UUID id) {
    return currentStockMapper.toDto(getById(id));
  }

  public void edit(UUID id, EditCurrentStockDto dto) {
    CurrentStock cs = getById(id);
    currentStockMapper.updateEntity(dto, cs);

    UnitMeasurement maxUn = unitMeasurementRepository.findById(dto.getMaxQtyUnitId())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.UNIT_MEASUREMENT_NOT_FOUND));
    cs.setMaxQtyUnit(maxUn);

    UnitMeasurement minUn = unitMeasurementRepository.findById(dto.getMinQtyUnitId())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.UNIT_MEASUREMENT_NOT_FOUND));
    cs.setMinQtyUnit(minUn);

    // compatibility validation
    BaseUnit prodStockBaseUn = cs.getProduct().getStockUnit().getBaseUnit();

    if (maxUn.getBaseUnit() != prodStockBaseUn) {
      throw new AppValidationException("maxQtyUnitId", ErrorMessages.STOCK_PRODUCT_BASE_INCOMPATIBLE);
    }

    if (minUn.getBaseUnit() != prodStockBaseUn) {
      throw new AppValidationException("minQtyUnitId", ErrorMessages.STOCK_PRODUCT_BASE_INCOMPATIBLE);
    }

    // logical range validation
    if (dto.getMaxQty() * maxUn.getConversionFactor() > dto.getMinQty() * minUn.getConversionFactor()) {
      throw new BadRequestException(ErrorMessages.STOCK_INVALID_MAX_MIN_RANGE);
    }
  }

  public boolean checkStockAvailability(Product prod, Double qty) {
    return currentStockRepository.sumQuantityByProductId(prod.getId()) > qty;
  }

  public boolean checkStockAvailability(Warehouse wh, Product prod, Double qty) {
    CurrentStock stk = getByWarehouseProduct(wh, prod);
    return stk.getQty() > qty;
  }

  public boolean checkStockAvailability(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    if (prod.getStockUnit().getBaseUnit() != unit.getBaseUnit()) {
      throw new BadRequestException(ErrorMessages.UNIT_MEASUREMENT_BAD_CONVERSION);
    }
    return checkStockAvailability(wh, prod, qty * unit.getConversionFactor());
  }

  public void adjustStock(Warehouse wh, Product prod, Double qty) {
    CurrentStock stk = getStockOrCreateIfNotExists(wh, prod);
    stk.setQty(qty);

    // validate max qty rules
    if (stk.getMaxQty() > 0 && stk.quantityExceeded()) {
      throw new QuantityExceededException(qty, prod.getStockUnit(), stk.getMaxQty(), stk.getMaxQtyUnit());
    }

    currentStockRepository.save(stk);
  }

  public void increaseStock(Warehouse wh, Product prod, Double qty) {
    CurrentStock stk = getByWarehouseProduct(wh, prod);
    stk.increaseQuantity(qty);

    // validate max qty rules
    if (stk.getMaxQty() > 0 && stk.quantityExceeded()) {
      throw new QuantityExceededException(qty, prod.getStockUnit(), stk.getMaxQty(), stk.getMaxQtyUnit());
    }

    currentStockRepository.save(stk);
  }

  public void increaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    increaseStock(wh, prod, qty * unit.getConversionFactor());
  }

  public void decreaseStock(Warehouse wh, Product prod, Double qty) {
    CurrentStock stk = getByWarehouseProduct(wh, prod);
    stk.decreaseQuantity(qty);

    currentStockRepository.save(stk);
  }

  public void decreaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit) {
    decreaseStock(wh, prod, qty * unit.getConversionFactor());
  }

  private CurrentStock getById(UUID id) {
    return currentStockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.STOCK_NOT_FOUND));
  }

  private CurrentStock getByWarehouseProduct(Warehouse wh, Product prod) {
    return currentStockRepository.findByWarehouseAndProduct(wh, prod)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.STOCK_UNAVAILABLE));
  }

  private CurrentStock getStockOrCreateIfNotExists(Warehouse wh, Product prod) {
    Optional<CurrentStock> cs = currentStockRepository.findByWarehouseAndProduct(wh, prod);

    if (cs.isPresent()) {
      return cs.get();
    }

    return new CurrentStock(prod, wh, 0D, 0D, prod.getStockUnit(), 0D, prod.getStockUnit());
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
