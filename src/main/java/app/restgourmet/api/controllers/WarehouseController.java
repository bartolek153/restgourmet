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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.masterdata.dto.warehouse.WarehouseDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListFiltersDto;
import app.restgourmet.api.masterdata.service.spec.WarehouseService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/warehouses")
@Tag(name = "Warehouse", description = "Warehouses endpoints")
public class WarehouseController {

  private final WarehouseService warehouseService;

  public WarehouseController(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<WarehouseListDto>> listWarehouses(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      @ParameterObject final WarehouseListFiltersDto filters) {
    return ResponseEntity.ok(warehouseService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<WarehouseDto> getWarehouse(@PathVariable UUID id) {
    return ResponseEntity.ok(warehouseService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createWarehouse(@RequestBody @Valid WarehouseDto data) {
    UUID id = warehouseService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editWarehouse(@PathVariable UUID id, @Valid @RequestBody WarehouseDto dto) {
    warehouseService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteWarehouse(@PathVariable UUID id) {
    warehouseService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
