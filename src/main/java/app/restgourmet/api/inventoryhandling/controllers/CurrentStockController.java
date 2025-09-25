package app.restgourmet.api.inventoryhandling.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.stock.EditCurrentStockDto;
import app.restgourmet.api.inventoryhandling.service.spec.CurrentStockService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Current Stock", description = "Current Stock endpoints")
public class CurrentStockController {

  private final CurrentStockService currentStockService;

  public CurrentStockController(CurrentStockService currentStockService) {
    this.currentStockService = currentStockService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<CurrentStockListDto>> listStock(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "productId") final String sort,
      @ParameterObject final CurrentStockListFiltersDto filters) {
    return ResponseEntity.ok(currentStockService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CurrentStockDto> getStock(@PathVariable UUID id) {
    return ResponseEntity.ok(currentStockService.getOne(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateStock(@PathVariable UUID id, @RequestBody @Valid EditCurrentStockDto dto) {
    currentStockService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
