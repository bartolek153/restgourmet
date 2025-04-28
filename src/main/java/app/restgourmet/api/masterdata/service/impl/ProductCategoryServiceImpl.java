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
import app.restgourmet.api.masterdata.dto.category.ProdCategoryDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListDto;
import app.restgourmet.api.masterdata.dto.category.ProdCategoryListFiltersDto;
import app.restgourmet.api.masterdata.mappers.ProductCategoryMapper;
import app.restgourmet.api.masterdata.models.ProductCategory;
import app.restgourmet.api.masterdata.repository.ProductCategoryRepository;
import app.restgourmet.api.masterdata.repository.ProductFamilyRepository;
import app.restgourmet.api.masterdata.repository.specifications.ProductCategorySpecification;
import app.restgourmet.api.masterdata.service.spec.ProductCategoryService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

  private final ProductFamilyRepository productFamilyRepository;
  private final ProductCategoryRepository productCategoryRepository;

  @Autowired
  private ProductCategoryMapper productCategoryMapper;

  public ProductCategoryServiceImpl(ProductCategoryRepository prodCategoryRepository,
      ProductFamilyRepository productFamilyRepository) {
    this.productCategoryRepository = prodCategoryRepository;
    this.productFamilyRepository = productFamilyRepository;
  }

  @Override
  public PagedModel<ProdCategoryListDto> list(PageRequest pageReq, ProdCategoryListFiltersDto filters) {
    Specification<ProductCategory> spec = ProductCategorySpecification.filterBy(filters);
    Page<ProdCategoryListDto> res = productCategoryRepository.findAll(spec, pageReq)
        .map(productCategoryMapper::toListDto);

    return new PagedModel<>(res);
  }

  @Override
  public ProdCategoryDto getOne(UUID id) {
    return productCategoryMapper.toDto(getById(id));
  }

  @Override
  public UUID create(ProdCategoryDto dto) {
    ProductCategory cat = productCategoryMapper.toEntity(dto);
    cat = productCategoryRepository.save(cat);
    return cat.getId();
  }

  @Override
  public void edit(UUID id, ProdCategoryDto dto) {
    ProductCategory cat = getById(id);
    productCategoryMapper.updateEntity(dto, cat);
    productCategoryRepository.save(cat);
  }

  @Override
  public void delete(UUID id) {
    if (!productCategoryRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND);
    }

    if (productFamilyRepository.existsByCategoryId(id)) {
      throw new BadRequestException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_DELETE_DEPS);
    }

    productCategoryRepository.deleteById(id);
  }

  private ProductCategory getById(UUID id) {
    return productCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND));
  }
}