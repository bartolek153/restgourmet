package app.restgourmet.api.procurement.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.repository.BusinessPartnerRepository;
import app.restgourmet.api.procurement.dto.vendor.VendorDto;
import app.restgourmet.api.procurement.dto.vendor.VendorListDto;
import app.restgourmet.api.procurement.dto.vendor.VendorListFiltersDto;
import app.restgourmet.api.procurement.mappers.VendorMapper;
import app.restgourmet.api.procurement.models.Vendor;
import app.restgourmet.api.procurement.repository.VendorRepository;
import app.restgourmet.api.procurement.repository.specifications.VendorSpecification;
import app.restgourmet.api.procurement.service.spec.VendorService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class VendorServiceImpl implements VendorService {

  private final BusinessPartnerRepository businessPartnerRepository;
  private final VendorRepository vendorRepository;
  private final VendorMapper vendorMapper;

  public VendorServiceImpl(
      VendorRepository vendorRepository,
      VendorMapper vendorMapper,
      BusinessPartnerRepository businessPartnerRepository) {
    this.vendorRepository = vendorRepository;
    this.vendorMapper = vendorMapper;
    this.businessPartnerRepository = businessPartnerRepository;
  }

  @Override
  public PagedModel<VendorListDto> list(PageRequest pageReq, VendorListFiltersDto filters) {
    Specification<Vendor> spec = VendorSpecification.filterBy(filters);
    Page<Vendor> page = vendorRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(vendorMapper::toListDto));
  }

  @Override
  public VendorDto getOne(UUID id) {
    return vendorMapper.toDto(getById(id));
  }

  @Override
  @Transactional
  public UUID create(VendorDto dto) {
    if (dto.getPartnerId() == null || !businessPartnerRepository.existsById(dto.getPartnerId())) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.BUSINESS_PARTNER_NOT_FOUND);
    }

    if (vendorRepository.existsByBusinessPartnerId(dto.getPartnerId())) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.CUSTOMER_ALREADY_EXISTS);
    }

    Vendor vendor = vendorMapper.toEntity(dto);
    vendor.setBusinessPartner(businessPartnerRepository.getReferenceById(dto.getPartnerId()));

    vendor = vendorRepository.save(vendor);
    return vendor.getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, VendorDto dto) {
    Vendor vendor = getById(id);

    vendorMapper.updateEntity(dto, vendor);
    vendorRepository.save(vendor);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    if (!vendorRepository.existsByBusinessPartnerId(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.VENDOR_NOT_FOUND);
    }
    vendorRepository.deleteByBusinessPartnerId(id);
  }

  private Vendor getById(UUID id) {
    return vendorRepository.findByBusinessPartnerId(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.VENDOR_NOT_FOUND));
  }
}
