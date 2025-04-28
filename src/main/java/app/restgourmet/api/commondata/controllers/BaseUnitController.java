package app.restgourmet.api.commondata.controllers;

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

import app.restgourmet.api.commondata.dto.unit.BaseUnitDto;
import app.restgourmet.api.commondata.dto.unit.BaseUnitListDto;
import app.restgourmet.api.commondata.dto.unit.BaseUnitListFiltersDto;
import app.restgourmet.api.commondata.service.spec.BaseUnitService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/units")
@Tag(name = "Base Unit", description = "Basic units endpoints")
public class BaseUnitController {

  private final BaseUnitService baseUnitService;

  public BaseUnitController(BaseUnitService baseUnitService) {
    this.baseUnitService = baseUnitService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<BaseUnitListDto>> listBaseUnits(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final BaseUnitListFiltersDto filters) {
    return ResponseEntity.ok(
        baseUnitService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BaseUnitDto> getBaseUnit(@PathVariable UUID id) {
    return ResponseEntity.ok(baseUnitService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createBaseUnit(@RequestBody @Valid BaseUnitDto dto) {
    UUID id = baseUnitService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateBaseUnit(@PathVariable UUID id, @RequestBody @Valid BaseUnitDto dto) {
    baseUnitService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBaseUnit(@PathVariable UUID id) {
    baseUnitService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
