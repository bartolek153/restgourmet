package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.UnitMeasurementDto;
import app.restgourmet.api.masterdata.dto.UnitMeasurementListDto;
import app.restgourmet.api.masterdata.dto.UnitMeasurementListFiltersDto;

public interface IUnitMeasurementService {
  public PagedModel<UnitMeasurementListDto> list(PageRequest pageReq, UnitMeasurementListFiltersDto filters);

  public UnitMeasurementDto getOne(UUID id);

  public UUID create(UnitMeasurementDto dto);

  public void edit(UUID id, UnitMeasurementDto dto);

  public void delete(UUID id);
}
