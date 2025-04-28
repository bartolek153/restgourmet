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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.masterdata.dto.family.ProdFamilyDto;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyListDto;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyListFiltersDto;
import app.restgourmet.api.masterdata.service.spec.ProductFamilyService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/families")
@Tag(name = "Product Family", description = "Product families endpoints")
public class ProductFamilyController {

  private final ProductFamilyService productFamilyService;

  public ProductFamilyController(ProductFamilyService productFamilyService) {
    this.productFamilyService = productFamilyService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ProdFamilyListDto>> listFamilies(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final ProdFamilyListFiltersDto filters) {
    return ResponseEntity.ok(
        productFamilyService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdFamilyDto> getFamily(@PathVariable UUID id) {
    return ResponseEntity.ok(productFamilyService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createFamily(@RequestBody @Valid ProdFamilyDto dto) {
    UUID id = productFamilyService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateFamily(@PathVariable UUID id, @RequestBody @Valid ProdFamilyDto dto) {
    productFamilyService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFamily(@PathVariable UUID id) {
    productFamilyService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
