package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyDto;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyListDto;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyListFiltersDto;
import app.restgourmet.api.masterdata.mappers.ProductFamilyMapper;
import app.restgourmet.api.masterdata.models.ProductFamily;
import app.restgourmet.api.masterdata.repository.ProductCategoryRepository;
import app.restgourmet.api.masterdata.repository.ProductFamilyRepository;
import app.restgourmet.api.masterdata.repository.ProductGroupRepository;
import app.restgourmet.api.masterdata.repository.specifications.ProductFamilySpecification;
import app.restgourmet.api.masterdata.service.spec.ProductFamilyService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class ProductFamilyServiceImpl implements ProductFamilyService {

  private final ProductGroupRepository productGroupRepository;

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductFamilyRepository productFamilyRepository;

  @Autowired
  private ProductFamilyMapper productFamilyMapper;

  public ProductFamilyServiceImpl(
      ProductFamilyRepository productFamilyRepository,
      ProductCategoryRepository productCategoryRepository, 
      ProductGroupRepository productGroupRepository) {
    this.productFamilyRepository = productFamilyRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.productGroupRepository = productGroupRepository;
  }

  @Override
  public PagedModel<ProdFamilyListDto> list(PageRequest pageReq, ProdFamilyListFiltersDto filters) {
    Specification<ProductFamily> spec = ProductFamilySpecification.filterBy(filters);
    Page<ProductFamily> families = productFamilyRepository.findAll(spec, pageReq);
    return new PagedModel<>(families.map(productFamilyMapper::toListDto));
  }

  @Override
  public ProdFamilyDto getOne(UUID id) {
    ProductFamily family = getById(id);
    return productFamilyMapper.toDto(family);
  }

  @Override
  public UUID create(ProdFamilyDto dto) {
    ProductFamily ent = productFamilyMapper.toEntity(dto);

    if (dto.getCategoryId() != null) {
      if (!productCategoryRepository.existsById(dto.getCategoryId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND);
      }
      ent.setCategory(productCategoryRepository.getReferenceById(dto.getCategoryId()));
    }

    return productFamilyRepository.save(ent).getId();
  }

  @Override
  public void edit(UUID id, ProdFamilyDto dto) {
    ProductFamily ent = getById(id);
    productFamilyMapper.updateEntity(dto, ent);

    if (dto.getCategoryId() != null) {
      if (!productCategoryRepository.existsById(dto.getCategoryId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND);
      }
      ent.setCategory(productCategoryRepository.getReferenceById(dto.getCategoryId()));
    }

    productFamilyRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!productFamilyRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_FAMILY_NOT_FOUND);
    }

    if (productGroupRepository.existsByFamilyId(id)) {
      throw new BadRequestException(AppConstants.ErrorMessages.PRODUCT_FAMILY_DELETE_DEPS);
    }

    productFamilyRepository.deleteById(id);
  }

  private ProductFamily getById(UUID id) {
    return productFamilyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_FAMILY_NOT_FOUND));
  }
}
