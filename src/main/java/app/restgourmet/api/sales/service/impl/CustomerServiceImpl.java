package app.restgourmet.api.sales.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.models.BusinessPartner;
import app.restgourmet.api.masterdata.repository.AddressRepository;
import app.restgourmet.api.masterdata.repository.BusinessPartnerRepository;
import app.restgourmet.api.sales.dto.CustomerDto;
import app.restgourmet.api.sales.dto.CustomerListDto;
import app.restgourmet.api.sales.dto.CustomerListFiltersDto;
import app.restgourmet.api.sales.mappers.CustomerMapper;
import app.restgourmet.api.sales.models.Customer;
import app.restgourmet.api.sales.repository.CustomerRepository;
import app.restgourmet.api.sales.repository.specifications.CustomerSpec;
import app.restgourmet.api.sales.service.spec.CustomerService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final BusinessPartnerRepository businessPartnerRepository;
  private final CustomerRepository customerRepository;
  private final AddressRepository addressRepository;
  private final CustomerMapper customerMapper;

  public CustomerServiceImpl(
      BusinessPartnerRepository businessPartnerRepository,
      CustomerRepository customerRepository,
      AddressRepository addressRepository,
      CustomerMapper customerMapper) {
    this.businessPartnerRepository = businessPartnerRepository;
    this.customerRepository = customerRepository;
    this.addressRepository = addressRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public PagedModel<CustomerListDto> list(PageRequest pageReq, CustomerListFiltersDto filters) {
    Specification<Customer> spec = CustomerSpec.filterBy(filters);
    Page<Customer> page = customerRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(customerMapper::toListDto));
  }

  @Override
  public CustomerDto getOne(UUID id) {
    return customerMapper.toDto(getById(id));
  }

  @Override
  @Transactional
  public UUID create(UUID partnerId, CustomerDto dto) {
    if (customerRepository.existsById(partnerId)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.CUSTOMER_ALREADY_EXISTS);
    }

    BusinessPartner bp = getBpById(partnerId);

    if (!addressRepository.existsById(dto.getBillingAddressId())) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
    }

    Customer customer = new Customer(
        bp.getId(),
        addressRepository.getReferenceById(dto.getBillingAddressId()));
        
    customer = customerRepository.save(customer);
    return customer.getId();
  }

  @Override
  @Transactional
  public void edit(UUID partnerId, CustomerDto dto) {
    Customer customer = getById(partnerId);

    if (!dto.getBillingAddressId().equals(customer.getBillingAddress().getId())) {
      if (!addressRepository.existsById(dto.getBillingAddressId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
      }
      customer.setBillingAddress(addressRepository.getReferenceById(dto.getBillingAddressId()));
    }

    customerMapper.updateEntity(dto, customer);
    customerRepository.save(customer);
  }

  @Override
  public void delete(UUID id) {
    if (!customerRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_NOT_FOUND);
    }
    customerRepository.deleteById(id);
  }

  private Customer getById(UUID id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.CUSTOMER_NOT_FOUND));
  }

  private BusinessPartner getBpById(UUID id) {
    return businessPartnerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.BUSINESS_PARTNER_NOT_FOUND));
  }
}
