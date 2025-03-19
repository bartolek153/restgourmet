package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.ProdGroupDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListFiltersDto;

public interface IProductGroupService {
  PagedModel<ProdGroupListDto> list(PageRequest pageReq, ProdGroupListFiltersDto filters);

  ProdGroupDto getOne(UUID id);

  UUID create(ProdGroupDto dto);

  void edit(UUID id, ProdGroupDto dto);

  void delete(UUID id);
}
