package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.ListProdGroupFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupListDto;

@Service
public interface IProductGroupService {
  public PagedModel<ProdGroupListDto> list(PageRequest pageReq, ListProdGroupFiltersDto filters);

  public ProdGroupDto getOne(UUID id);

  public UUID create(ProdGroupDto dto);

  public void edit(UUID id, ProdGroupDto dto);

  public void delete(UUID id);
}
