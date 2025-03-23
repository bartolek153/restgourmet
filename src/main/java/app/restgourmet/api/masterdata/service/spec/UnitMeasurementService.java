package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListFiltersDto;

public interface UnitMeasurementService {
  PagedModel<UnitMeasurementListDto> list(PageRequest pageReq, UnitMeasurementListFiltersDto filters);

  UnitMeasurementDto getOne(UUID id);

  UUID create(UnitMeasurementDto dto);

  void edit(UUID id, UnitMeasurementDto dto);

  void delete(UUID id);
}
