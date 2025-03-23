package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.group.ProdGroupDto;
import app.restgourmet.api.masterdata.dto.group.ProdGroupListDto;
import app.restgourmet.api.masterdata.dto.group.ProdGroupListFiltersDto;

public interface ProductGroupService {
  PagedModel<ProdGroupListDto> list(PageRequest pageReq, ProdGroupListFiltersDto filters);

  ProdGroupDto getOne(UUID id);

  UUID create(ProdGroupDto dto);

  void edit(UUID id, ProdGroupDto dto);

  void delete(UUID id);
}
