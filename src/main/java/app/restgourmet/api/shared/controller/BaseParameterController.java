package app.restgourmet.api.shared.controller;

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

import app.restgourmet.api.shared.dto.ParameterListFiltersDto;
import app.restgourmet.api.shared.models.parameters.BaseParameter;
import app.restgourmet.api.shared.service.spec.ParameterService;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parameters")
@Tag(name = "Parameter", description = "Parameter endpoints")
public abstract class BaseParameterController<T extends BaseParameter> {

  protected final ParameterService<T> parameterService;

  public BaseParameterController(ParameterService<T> parameterService) {
    this.parameterService = parameterService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<T>> listParameters(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "description") final String sort,
      @ParameterObject final ParameterListFiltersDto filters) {

    return ResponseEntity.ok(
        parameterService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<T> getParameter(@PathVariable UUID id) {
    return ResponseEntity.ok(parameterService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createParameter(@RequestBody @Valid T param) {
    UUID id = parameterService.create(param);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateParameter(@PathVariable UUID id, @RequestBody @Valid T param) {
    parameterService.edit(id, param);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteParameter(@PathVariable UUID id) {
    parameterService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
