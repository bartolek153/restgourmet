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

import app.restgourmet.api.masterdata.dto.address.AddressDto;
import app.restgourmet.api.masterdata.dto.address.AddressListDto;
import app.restgourmet.api.masterdata.dto.address.AddressListFiltersDto;
import app.restgourmet.api.masterdata.service.spec.AddressService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Address", description = "Addresses endpoints")
public class AddressController {

  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<AddressListDto>> listAddresses(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "street") final String sort,
      @ParameterObject final AddressListFiltersDto filters) {
    return ResponseEntity.ok(addressService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AddressDto> getAddress(@PathVariable UUID id) {
    return ResponseEntity.ok(addressService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createAddress(@RequestBody @Valid AddressDto data) {
    UUID id = addressService.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editAddress(@PathVariable UUID id, @Valid @RequestBody AddressDto dto) {
    addressService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAddress(@PathVariable UUID id) {
    addressService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/cep/{cep}")
  public ResponseEntity<AddressDto> getAddressCep(@PathVariable String cep) {
    return ResponseEntity.ok(addressService.consultBrazilianAddress(cep));
  }
}
