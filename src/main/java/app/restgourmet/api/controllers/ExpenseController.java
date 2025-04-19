package app.restgourmet.api.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.procurement.dto.expense.CreateExpenseDto;
import app.restgourmet.api.procurement.dto.expense.EditExpenseDto;
import app.restgourmet.api.procurement.dto.expense.ExpenseDto;
import app.restgourmet.api.procurement.dto.expense.ExpenseListDto;
import app.restgourmet.api.procurement.dto.expense.ExpenseListFiltersDto;
import app.restgourmet.api.procurement.service.spec.ExpenseService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense", description = "Expenses endpoints")
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ExpenseListDto>> listExpensees(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "street") final String sort,
      @ParameterObject final ExpenseListFiltersDto filters) {
    return ResponseEntity.ok(expenseService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseDto> getExpense(@PathVariable UUID id) {
    return ResponseEntity.ok(expenseService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createExpense(@RequestBody @Valid CreateExpenseDto data) {
    UUID id = expenseService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editExpense(@PathVariable UUID id, @Valid @RequestBody EditExpenseDto dto) {
    expenseService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteExpense(@PathVariable UUID id) {
    expenseService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
