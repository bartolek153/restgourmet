package app.restgourmet.api.sales.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryDto;
import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListDto;
import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListFiltersDto;

public interface SalesOrderCategoryService {
  PagedModel<SalesOrderCategoryListDto> list(PageRequest pageReq, SalesOrderCategoryListFiltersDto filters);

  SalesOrderCategoryDto getOne(UUID id);

  UUID create(SalesOrderCategoryDto dto);

  void edit(UUID id, SalesOrderCategoryDto dto);

  void delete(UUID id);
}
