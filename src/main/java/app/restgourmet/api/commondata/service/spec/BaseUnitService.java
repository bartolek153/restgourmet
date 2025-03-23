package app.restgourmet.api.commondata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.commondata.dto.unit.BaseUnitDto;
import app.restgourmet.api.commondata.dto.unit.BaseUnitListDto;
import app.restgourmet.api.commondata.dto.unit.BaseUnitListFiltersDto;

public interface BaseUnitService {
  PagedModel<BaseUnitListDto> list(PageRequest pagReq, BaseUnitListFiltersDto filters);

  BaseUnitDto getOne(UUID id);

  UUID create(BaseUnitDto dto);

  void edit(UUID id, BaseUnitDto dto);

  void delete(UUID id);
}
