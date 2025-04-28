package app.restgourmet.api.masterdata.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.masterdata.dto.category.ProdCategoryDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListFiltersDto;
import app.restgourmet.api.masterdata.service.spec.ProductCategoryService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/products/categories")
@Tag(name = "Product Category", description = "Product categories endpoints")
public class ProductCategoryController {

  private final ProductCategoryService productCategoryService;

  public ProductCategoryController(ProductCategoryService prodCategoryService) {
    productCategoryService = prodCategoryService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ProdCategoryListDto>> listCategories(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final ProdCategoryListFiltersDto filters) {
    return ResponseEntity.ok(productCategoryService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdCategoryDto> getCategory(@PathVariable UUID id) {
    return ResponseEntity.ok(productCategoryService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createCategory(@RequestBody @Valid ProdCategoryDto data) {
    UUID id = productCategoryService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editCategory(@PathVariable UUID id, @Valid @RequestBody ProdCategoryDto dto) {
    productCategoryService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
    productCategoryService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
