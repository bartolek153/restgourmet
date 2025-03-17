package app.restgourmet.api.commondata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.commondata.dto.BaseUnitDto;
import app.restgourmet.api.commondata.dto.BaseUnitListDto;
import app.restgourmet.api.commondata.dto.BaseUnitListFiltersDto;
import app.restgourmet.api.commondata.mappers.IBaseUnitMapper;
import app.restgourmet.api.commondata.models.BaseUnit;
import app.restgourmet.api.commondata.repository.BaseUnitRepository;
import app.restgourmet.api.commondata.service.spec.IBaseUnitService;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CommonUtils;

@Service
public class BaseUnitService implements IBaseUnitService {

  private final BaseUnitRepository baseUnitRepository;

  @Autowired
  private IBaseUnitMapper baseUnitMapper;

  public BaseUnitService(BaseUnitRepository baseUnitRepository) {
    this.baseUnitRepository = baseUnitRepository;
  }

  @Override
  public PagedModel<BaseUnitListDto> list(PageRequest pagReq, BaseUnitListFiltersDto filters) {
    Page<BaseUnit> units;

    if (filters.isEmpty()) {
      units = baseUnitRepository.findAll(pagReq);
    } else {
      UUID id = CommonUtils.parseUUID(filters.getQ());
      units = baseUnitRepository.findByIdOrDescriptionContainingIgnoreCase(id, filters.getQ(), pagReq);
    }

    return new PagedModel<>(units.map(baseUnitMapper::toListDto));
  }

  @Override
  public BaseUnitDto getOne(UUID id) {
    BaseUnit unit = getById(id);
    return baseUnitMapper.toDto(unit);
  }

  @Override
  public UUID create(BaseUnitDto dto) {
    BaseUnit ent = baseUnitMapper.toEntity(dto);
    return baseUnitRepository.save(ent).getId();
  }

  @Override
  public void edit(UUID id, BaseUnitDto dto) {
    BaseUnit ent = getById(id);

    baseUnitMapper.updateEntity(dto, ent);
    baseUnitRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!baseUnitRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.BASE_UNIT_NOT_FOUND);
    }

    baseUnitRepository.deleteById(id);
  }

  private BaseUnit getById(UUID id) {
    return baseUnitRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.BASE_UNIT_NOT_FOUND));
  }
}
