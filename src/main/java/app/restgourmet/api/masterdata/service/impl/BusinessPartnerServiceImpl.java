package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListFiltersDto;
import app.restgourmet.api.masterdata.dto.businesspartner.CreateBusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.EditBusinessPartnerDto;
import app.restgourmet.api.masterdata.enums.BusinessPartnerStatus;
import app.restgourmet.api.masterdata.mappers.BusinessPartnerMapper;
import app.restgourmet.api.masterdata.models.BusinessPartner;
import app.restgourmet.api.masterdata.repository.BusinessPartnerRepository;
import app.restgourmet.api.masterdata.repository.specifications.BusinessPartnerSpec;
import app.restgourmet.api.masterdata.service.spec.BusinessPartnerService;
import app.restgourmet.api.sales.repository.CustomerRepository;
import app.restgourmet.api.utils.AppConstants;

@Service
public class BusinessPartnerServiceImpl implements BusinessPartnerService {

  private final BusinessPartnerRepository businessPartnerRepository;
  private final CustomerRepository customerRepository;

  @Autowired
  private BusinessPartnerMapper businessPartnerMapper;

  public BusinessPartnerServiceImpl(
      BusinessPartnerRepository businessPartnerRepository,
      CustomerRepository customerRepository) {
    this.businessPartnerRepository = businessPartnerRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public PagedModel<BusinessPartnerListDto> list(PageRequest pageReq, BusinessPartnerListFiltersDto filters) {
    Specification<BusinessPartner> spec = BusinessPartnerSpec.filterBy(filters);
    Page<BusinessPartnerListDto> res = businessPartnerRepository.findAll(spec, pageReq)
        .map(businessPartnerMapper::toListDto);

    return new PagedModel<>(res);
  }

  @Override
  public BusinessPartnerDto getOne(UUID id) {
    BusinessPartnerDto dto = businessPartnerMapper.toDto(getById(id));

    if (customerRepository.existsByBusinessPartnerId(id)) {
      dto.setCustomer(true);
    }
    return dto;
  }

  @Override
  public UUID create(CreateBusinessPartnerDto dto) {
    BusinessPartner businessPartner = businessPartnerMapper.toEntity(dto);
    businessPartner.setStatus(BusinessPartnerStatus.ACTIVE);

    businessPartner = businessPartnerRepository.save(businessPartner);
    return businessPartner.getId();
  }

  @Override
  public void edit(UUID id, EditBusinessPartnerDto dto) {
    BusinessPartner businessPartner = getById(id);
    businessPartnerMapper.updateEntity(dto, businessPartner);
    businessPartnerRepository.save(businessPartner);
  }

  @Override
  public void delete(UUID id) {
    if (!businessPartnerRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.BUSINESS_PARTNER_NOT_FOUND);
    }

    businessPartnerRepository.deleteById(id);
  }

  private BusinessPartner getById(UUID id) {
    return businessPartnerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.BUSINESS_PARTNER_NOT_FOUND));
  }
}
