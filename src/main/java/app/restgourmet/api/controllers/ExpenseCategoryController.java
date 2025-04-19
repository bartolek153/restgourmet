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

import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListFiltersDto;
import app.restgourmet.api.procurement.service.spec.ExpenseCategoryService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses/categories")
@Tag(name = "Expense Category", description = "Expense categories endpoints")
public class ExpenseCategoryController {

  private final ExpenseCategoryService expenseCategoryService;

  public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
    this.expenseCategoryService = expenseCategoryService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ExpenseCategoryListDto>> listCategories(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "street") final String sort,
      @ParameterObject final ExpenseCategoryListFiltersDto filters) {
    return ResponseEntity.ok(expenseCategoryService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseCategoryDto> getCategory(@PathVariable UUID id) {
    return ResponseEntity.ok(expenseCategoryService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createCategory(@RequestBody @Valid ExpenseCategoryDto data) {
    UUID id = expenseCategoryService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editCategory(@PathVariable UUID id, @Valid @RequestBody ExpenseCategoryDto dto) {
    expenseCategoryService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
    expenseCategoryService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
