package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.BadGateway;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.dto.address.AddressDto;
import app.restgourmet.api.masterdata.dto.address.AddressListDto;
import app.restgourmet.api.masterdata.dto.address.AddressListFiltersDto;
import app.restgourmet.api.masterdata.dto.address.ViaCepAddressDto;
import app.restgourmet.api.masterdata.mappers.AddressMapper;
import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.masterdata.repository.AddressRepository;
import app.restgourmet.api.masterdata.repository.specifications.AddressSpec;
import app.restgourmet.api.masterdata.service.spec.AddressService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final RestTemplate restTemplate;

  private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

  @Autowired
  private AddressMapper addressMapper;

  public AddressServiceImpl(
      AddressRepository addressRepository,
      RestTemplateBuilder restTemplateBuilder) {
    this.addressRepository = addressRepository;
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public PagedModel<AddressListDto> list(PageRequest pageReq, AddressListFiltersDto filters) {
    Specification<Address> spec = AddressSpec.filterBy(filters);
    Page<AddressListDto> res = addressRepository.findAll(spec, pageReq)
        .map(addressMapper::toListDto);

    return new PagedModel<>(res);
  }

  @Override
  public AddressDto getOne(UUID id) {
    return addressMapper.toDto(getById(id));
  }

  @Override
  public UUID create(AddressDto dto) {
    Address cat = addressMapper.toEntity(dto);
    cat = addressRepository.save(cat);
    return cat.getId();
  }

  @Override
  public void edit(UUID id, AddressDto dto) {
    Address cat = getById(id);
    addressMapper.updateEntity(dto, cat);
    addressRepository.save(cat);
  }

  @Override
  public void delete(UUID id) {
    if (!addressRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
    }

    addressRepository.deleteById(id);
  }

  private Address getById(UUID id) {
    return addressRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND));
  }

  @Override
  public AddressDto consultBrazilianAddress(String cep) {
    try {
      ViaCepAddressDto response = restTemplate.getForObject(AppConstants.ExternalServices.Urls.VIACEP,
          ViaCepAddressDto.class, cep);
      return response.toAddressDto();

    } catch (HttpClientErrorException.NotFound ex) {

      // Specific handling for 404
      log.error("Address not found for CEP {}", cep);
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);

    } catch (HttpClientErrorException ex) {

      // Handle other 4xx errors
      log.error("Client error when fetching address for CEP {}: Status {}, Body: {}",
          cep, ex.getStatusCode(), ex.getResponseBodyAsString());
      throw new BadRequestException(AppConstants.ErrorMessages.CEP_INVALID_FORMAT);

    } catch (HttpServerErrorException ex) {

      // Handle 5xx errors
      log.error("Server error from ViaCEP when fetching address for CEP {}: Status {}",
          cep, ex.getStatusCode());
      throw BadGateway.create(HttpStatus.BAD_GATEWAY, ex.getStatusCode().toString(),
          null, null, null);

    } catch (RestClientException ex) {

      // Handle other REST client exceptions (timeouts, etc.)
      log.error("Communication error with ViaCEP service for CEP {}: {}", cep, ex.getMessage());
      throw BadGateway.create(HttpStatus.SERVICE_UNAVAILABLE, 
      "", null, null, null);
      
    }
  }
}