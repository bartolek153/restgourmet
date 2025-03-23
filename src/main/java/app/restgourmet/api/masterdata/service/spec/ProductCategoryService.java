package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.category.ProdCategoryDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListFiltersDto;

public interface ProductCategoryService {
  PagedModel<ProdCategoryListDto> list(PageRequest pageReq, ProdCategoryListFiltersDto filters);

  ProdCategoryDto getOne(UUID id);

  UUID create(ProdCategoryDto dto);

  void edit(UUID id, ProdCategoryDto dto);

  void delete(UUID id);
}
