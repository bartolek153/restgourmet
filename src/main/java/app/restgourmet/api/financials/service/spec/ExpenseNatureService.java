package app.restgourmet.api.financials.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListFiltersDto;

public interface ExpenseNatureService {
  PagedModel<ExpenseNatureListDto> list(PageRequest pageReq, ExpenseNatureListFiltersDto filters);

  ExpenseNatureDto getOne(UUID id);

  UUID create(ExpenseNatureDto dto);

  void edit(UUID id, ExpenseNatureDto dto);

  void delete(UUID id);
}
