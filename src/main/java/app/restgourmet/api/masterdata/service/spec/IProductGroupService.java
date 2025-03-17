package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.ProdGroupDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListFiltersDto;

public interface IProductGroupService {
  public PagedModel<ProdGroupListDto> list(PageRequest pageReq, ProdGroupListFiltersDto filters);

  public ProdGroupDto getOne(UUID id);

  public UUID create(ProdGroupDto dto);

  public void edit(UUID id, ProdGroupDto dto);

  public void delete(UUID id);
}
