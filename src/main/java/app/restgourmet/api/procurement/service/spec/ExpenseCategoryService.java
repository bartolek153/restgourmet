package app.restgourmet.api.procurement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListFiltersDto;

public interface ExpenseCategoryService {
  PagedModel<ExpenseCategoryListDto> list(PageRequest pageReq, ExpenseCategoryListFiltersDto filters);

  ExpenseCategoryDto getOne(UUID id);

  UUID create(ExpenseCategoryDto dto);

  void edit(UUID id, ExpenseCategoryDto dto);

  void delete(UUID id);
}
