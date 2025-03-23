package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.commondata.repository.BaseUnitRepository;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListFiltersDto;
import app.restgourmet.api.masterdata.mappers.UnitMeasurementMapper;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.repository.UnitMeasurementRepository;
import app.restgourmet.api.masterdata.repository.specifications.UnitMeasSpec;
import app.restgourmet.api.masterdata.service.spec.UnitMeasurementService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class UnitMeasurementServiceImpl implements UnitMeasurementService {

  private final BaseUnitRepository baseUnitRepository;
  private final UnitMeasurementRepository unitMeasurementRepository;

  @Autowired
  private UnitMeasurementMapper unitMeasurementMapper;

  public UnitMeasurementServiceImpl(UnitMeasurementRepository unitMeasurementRepository,
      BaseUnitRepository productFamilyRepository) {
    this.unitMeasurementRepository = unitMeasurementRepository;
    this.baseUnitRepository = productFamilyRepository;
  }

  @Override
  public PagedModel<UnitMeasurementListDto> list(PageRequest pageReq, UnitMeasurementListFiltersDto filters) {
    Specification<UnitMeasurement> spec = UnitMeasSpec.filterBy(filters);
    Page<UnitMeasurementListDto> units = unitMeasurementRepository.findAll(spec, pageReq).map(
        unitMeasurementMapper::toListDto);

    return new PagedModel<>(units);
  }

  @Override
  public UnitMeasurementDto getOne(UUID id) {
    return unitMeasurementMapper.toDto(getById(id));
  }

  @Override
  public UUID create(UnitMeasurementDto dto) {
    UnitMeasurement ent = unitMeasurementMapper.toEntity(dto);

    if (dto.getBaseUnitId() != null) {
      setBaseUnitFromDto(dto, ent);
    }

    return unitMeasurementRepository.save(ent).getId();
  }

  @Override
  public void edit(UUID id, UnitMeasurementDto dto) {
    UnitMeasurement ent = getById(id);
    unitMeasurementMapper.updateEntity(dto, ent);

    if (dto.getBaseUnitId() != null) {
      setBaseUnitFromDto(dto, ent);
    }

    unitMeasurementRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!unitMeasurementRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.UNIT_MEASUREMENT_NOT_FOUND);
    }

    unitMeasurementRepository.deleteById(id);
  }

  private UnitMeasurement getById(UUID id) {
    return unitMeasurementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
  }

  private void setBaseUnitFromDto(UnitMeasurementDto dto, UnitMeasurement ent) {
    if (!baseUnitRepository.existsById(dto.getBaseUnitId())) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.BASE_UNIT_NOT_FOUND);
    }
    ent.setBaseUnit(baseUnitRepository.getReferenceById(dto.getBaseUnitId()));
  }
}
