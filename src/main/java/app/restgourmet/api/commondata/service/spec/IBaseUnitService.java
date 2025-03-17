package app.restgourmet.api.commondata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.commondata.dto.BaseUnitDto;
import app.restgourmet.api.commondata.dto.BaseUnitListDto;
import app.restgourmet.api.commondata.dto.BaseUnitListFiltersDto;

public interface IBaseUnitService {
  PagedModel<BaseUnitListDto> list(PageRequest pagReq, BaseUnitListFiltersDto filters);

  BaseUnitDto getOne(UUID id);

  UUID create(BaseUnitDto dto);

  void edit(UUID id, BaseUnitDto dto);

  void delete(UUID id);
}
