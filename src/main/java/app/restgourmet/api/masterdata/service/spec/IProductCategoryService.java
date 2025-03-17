package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.ProdCategoryDto;
import app.restgourmet.api.masterdata.dto.ProdCategoryListDto;
import app.restgourmet.api.masterdata.dto.ProdCategoryListFiltersDto;

public interface IProductCategoryService {
  public PagedModel<ProdCategoryListDto> list(PageRequest pageReq, ProdCategoryListFiltersDto filters);

  public ProdCategoryDto getOne(UUID id);

  public UUID create(ProdCategoryDto dto);

  public void edit(UUID id, ProdCategoryDto dto);

  public void delete(UUID id);
}
