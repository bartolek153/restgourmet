package app.restgourmet.api.financials.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.financials.dto.expense.CreateExpenseDto;
import app.restgourmet.api.financials.dto.expense.EditExpenseDto;
import app.restgourmet.api.financials.dto.expense.ExpenseDto;
import app.restgourmet.api.financials.dto.expense.ExpenseListDto;
import app.restgourmet.api.financials.dto.expense.ExpenseListFiltersDto;

public interface ExpenseService {
  PagedModel<ExpenseListDto> list(PageRequest pagReq, ExpenseListFiltersDto filters);

  ExpenseDto getOne(UUID id);

  UUID create(CreateExpenseDto dto);

  void edit(UUID id, EditExpenseDto dto);

  void delete(UUID id);
}
