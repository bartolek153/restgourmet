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

import app.restgourmet.api.inventoryhandling.dto.ListProdGroupFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupListDto;
import app.restgourmet.api.inventoryhandling.service.spec.IProductGroupService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/families")
@Tag(name = "Product Group", description = "Product families endpoints")
public class ProductGroupController {

  private final IProductGroupService productGroupService;

  public ProductGroupController(IProductGroupService productGroupService) {
    this.productGroupService = productGroupService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<ProdGroupListDto>> listFamilies(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final ListProdGroupFiltersDto filters) {
    return ResponseEntity.ok(
        productGroupService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdGroupDto> getGroup(@PathVariable UUID id) {
    return ResponseEntity.ok(productGroupService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createGroup(@RequestBody @Valid ProdGroupDto dto) {
    UUID id = productGroupService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateGroup(@PathVariable UUID id, @RequestBody @Valid ProdGroupDto dto) {
    productGroupService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
    productGroupService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
