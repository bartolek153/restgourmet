package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.masterdata.dto.warehouse.WarehouseDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListFiltersDto;
import app.restgourmet.api.masterdata.enums.WarehouseStatus;
import app.restgourmet.api.masterdata.mappers.WarehouseMapper;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.masterdata.repository.AddressRepository;
import app.restgourmet.api.masterdata.repository.WarehouseRepository;
import app.restgourmet.api.masterdata.repository.specifications.WarehouseSpec;
import app.restgourmet.api.masterdata.service.spec.WarehouseService;
import app.restgourmet.api.sales.enums.OrderStatus;
import app.restgourmet.api.sales.repository.SalesOrderRepository;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;

@Service
public class WarehouseServiceImpl implements WarehouseService {

  private final AddressRepository addressRepository;
  private final SalesOrderRepository salesOrderRepository;
  private final WarehouseRepository warehouseRepository;

  @Autowired
  private WarehouseMapper warehouseMapper;

  public WarehouseServiceImpl(
      AddressRepository addressRepository,
      WarehouseRepository prodCategoryRepository, 
      SalesOrderRepository salesOrderRepository) {
    this.addressRepository = addressRepository;
    this.warehouseRepository = prodCategoryRepository;
    this.salesOrderRepository = salesOrderRepository;
  }

  @Override
  public PagedModel<WarehouseListDto> list(PageRequest pageReq, WarehouseListFiltersDto filters) {
    Specification<Warehouse> spec = WarehouseSpec.filterBy(filters);
    Page<WarehouseListDto> res = warehouseRepository.findAll(spec, pageReq)
        .map(warehouseMapper::toListDto);

    return new PagedModel<>(res);
  }

  @Override
  public WarehouseDto getOne(UUID id) {
    return warehouseMapper.toDto(getById(id));
  }

  @Override
  public UUID create(WarehouseDto dto) {
    Warehouse wh = warehouseMapper.toEntity(dto);

    if (!addressRepository.existsById(dto.getAddressId())) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
    }

    wh.setAddress(addressRepository.getReferenceById(dto.getAddressId()));
    wh.setStatus(WarehouseStatus.ACTIVE);

    wh = warehouseRepository.save(wh);
    return wh.getId();
  }

  @Override
  public void edit(UUID id, WarehouseDto dto) {
    Warehouse wh = getById(id);

    if (dto.getAddressId() != null && !dto.getAddressId().equals(wh.getAddress().getId())) {
      if (!addressRepository.existsById(dto.getAddressId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
      }
      wh.setAddress(addressRepository.getReferenceById(dto.getAddressId()));
    }

    warehouseMapper.updateEntity(dto, wh);
    warehouseRepository.save(wh);
  }

  /*
   * Delete a warehouse. 
   * If the warehouse has pending orders, it will be marked as inactive instead of being deleted. 
   * If the warehouse is not found, a ResourceNotFoundException will be thrown. 
   * If the warehouse has pending orders, a BadRequestException will be thrown.
   */
  @Override
  public void delete(UUID id) {
    if (!warehouseRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.WAREHOUSE_NOT_FOUND);
    }

    if (salesOrderRepository.existsByWarehouseId(id)) {
      if (salesOrderRepository
          .existsByStatusNotIn(new OrderStatus[] { OrderStatus.CANCELLED, OrderStatus.DELIVERED })) {
        throw new BadRequestException(AppConstants.ErrorMessages.WAREHOUSE_HAS_PENDING_ORDERS);
      }

      Warehouse wh = getById(id);
      wh.setStatus(WarehouseStatus.INACTIVE);
      warehouseRepository.save(wh);
      return;
    }

    warehouseRepository.deleteById(id);
  }

  private Warehouse getById(UUID id) {
    return warehouseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.WAREHOUSE_NOT_FOUND));
  }
}