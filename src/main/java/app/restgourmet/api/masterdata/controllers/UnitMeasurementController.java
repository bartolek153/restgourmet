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

import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListDto;
import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListFiltersDto;
import app.restgourmet.api.masterdata.service.spec.UnitMeasurementService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/units/measurement")
@Tag(name = "Unit of Measurement", description = "Units of measurement endpoints")
public class UnitMeasurementController {

  private final UnitMeasurementService unitMeasurementService;

  public UnitMeasurementController(UnitMeasurementService unitMeasurementService) {
    this.unitMeasurementService = unitMeasurementService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<UnitMeasurementListDto>> listUnitsMeasurement(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final UnitMeasurementListFiltersDto filters) {
    return ResponseEntity.ok(
        unitMeasurementService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UnitMeasurementDto> getUnitMeasurement(@PathVariable UUID id) {
    return ResponseEntity.ok(unitMeasurementService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createUnitMeasurement(@RequestBody @Valid UnitMeasurementDto dto) {
    UUID id = unitMeasurementService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUnitMeasurement(@PathVariable UUID id, @RequestBody @Valid UnitMeasurementDto dto) {
    unitMeasurementService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUnitMeasurement(@PathVariable UUID id) {
    unitMeasurementService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
