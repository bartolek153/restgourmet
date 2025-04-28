package app.restgourmet.api.financials.controllers;

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

import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListFiltersDto;
import app.restgourmet.api.financials.service.spec.ExpenseNatureService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses/categories")
@Tag(name = "Expense Nature", description = "Expense natures endpoints")
public class ExpenseCategoryController {

  private final ExpenseNatureService expenseCategoryService;

  public ExpenseCategoryController(ExpenseNatureService expenseCategoryService) {
    this.expenseCategoryService = expenseCategoryService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ExpenseNatureListDto>> listCategories(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "street") final String sort,
      @ParameterObject final ExpenseNatureListFiltersDto filters) {
    return ResponseEntity.ok(expenseCategoryService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseNatureDto> getCategory(@PathVariable UUID id) {
    return ResponseEntity.ok(expenseCategoryService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createCategory(@RequestBody @Valid ExpenseNatureDto data) {
    UUID id = expenseCategoryService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editCategory(@PathVariable UUID id, @Valid @RequestBody ExpenseNatureDto dto) {
    expenseCategoryService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
    expenseCategoryService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
