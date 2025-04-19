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

import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListFiltersDto;
import app.restgourmet.api.commondata.service.spec.PaymentTermService;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermDto;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/payment-terms")
@Tag(name = "Payment Term", description = "Payment terms endpoints")
public class PaymentTermsController {
  private final PaymentTermService paymentTermService;

  public PaymentTermsController(PaymentTermService paymentTermService) {
    this.paymentTermService = paymentTermService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<PaymentTermListDto>> getTerms(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "description") final Direction order,
      @RequestParam(defaultValue = "ASC") final String sort,
      @ParameterObject PaymentTermListFiltersDto filters) {
    return ResponseEntity.ok(paymentTermService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentTermDto> getTerm(@PathVariable UUID id) {
    return ResponseEntity.ok(paymentTermService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createTerm(@RequestBody @Valid PaymentTermDto data) {
    UUID id = paymentTermService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editTerm(@PathVariable UUID id, @Valid @RequestBody PaymentTermDto dto) {
    paymentTermService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTerm(@PathVariable UUID id) {
    paymentTermService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
