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

import app.restgourmet.api.commondata.dto.currency.CurrencyDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListFiltersDto;
import app.restgourmet.api.commondata.service.spec.CurrencyService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/currencies")
@Tag(name = "Currency", description = "Currencies endpoints")
public class CurrencyController {
  
  private final CurrencyService currencyService;

  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<CurrencyListDto>> getCurrencies(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "description") final Direction order,
      @RequestParam(defaultValue = "ASC") final String sort,
      @ParameterObject CurrencyListFiltersDto filters) {
    return ResponseEntity.ok(currencyService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CurrencyDto> getCurrency(@PathVariable UUID id) {
    return ResponseEntity.ok(currencyService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createCurrency(@RequestBody @Valid CurrencyDto data) {
    UUID id = currencyService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editCurrency(@PathVariable UUID id, @Valid @RequestBody CurrencyDto dto) {
    currencyService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCurrency(@PathVariable UUID id) {
    currencyService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
