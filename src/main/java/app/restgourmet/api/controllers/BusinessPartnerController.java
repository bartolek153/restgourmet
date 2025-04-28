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

import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListFiltersDto;
import app.restgourmet.api.masterdata.dto.businesspartner.CreateBusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.EditBusinessPartnerDto;
import app.restgourmet.api.masterdata.service.spec.BusinessPartnerService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/partners")
@Tag(name = "Business Partner", description = "Business Partners endpoints")
public class BusinessPartnerController {

  private final BusinessPartnerService businessPartnerService;

  public BusinessPartnerController(BusinessPartnerService businessPartnerService) {
    this.businessPartnerService = businessPartnerService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<BusinessPartnerListDto>> listBusinessPartners(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      @ParameterObject final BusinessPartnerListFiltersDto filters) {
    return ResponseEntity.ok(businessPartnerService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BusinessPartnerDto> getBusinessPartner(@PathVariable UUID id) {
    return ResponseEntity.ok(businessPartnerService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createBusinessPartner(@RequestBody @Valid CreateBusinessPartnerDto data) {
    UUID id = businessPartnerService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editBusinessPartner(@PathVariable UUID id, @Valid @RequestBody EditBusinessPartnerDto dto) {
    businessPartnerService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBusinessPartner(@PathVariable UUID id) {
    businessPartnerService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
