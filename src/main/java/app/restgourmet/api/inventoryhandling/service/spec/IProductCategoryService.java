package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.inventoryhandling.dto.CreateProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.EditProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ListProdCategoryFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryListDto;

public interface IProductCategoryService {
  public PagedModel<ProdCategoryListDto> list(PageRequest pageReq, ListProdCategoryFiltersDto filters);

  public ProdCategoryDto getOne(UUID id);

  public UUID create(CreateProdCategoryDto dto);

  public void edit(UUID id, EditProdCategoryDto dto);

  public void delete(UUID id);
}
