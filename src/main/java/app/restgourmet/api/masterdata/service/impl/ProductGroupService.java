package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.dto.ProdGroupDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListDto;
import app.restgourmet.api.masterdata.dto.ProdGroupListFiltersDto;
import app.restgourmet.api.masterdata.mappers.IProductGroupMapper;
import app.restgourmet.api.masterdata.models.ProductGroup;
import app.restgourmet.api.masterdata.repository.ProductFamilyRepository;
import app.restgourmet.api.masterdata.repository.ProductGroupRepository;
import app.restgourmet.api.masterdata.repository.ProductRepository;
import app.restgourmet.api.masterdata.service.spec.IProductGroupService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CommonUtils;

@Service
public class ProductGroupService implements IProductGroupService {

  private final ProductRepository productRepository;

  private final ProductFamilyRepository productFamilyRepository;
  private final ProductGroupRepository productGroupRepository;

  @Autowired
  private IProductGroupMapper productGroupMapper;

  public ProductGroupService(ProductGroupRepository productGroupRepository,
      ProductFamilyRepository productFamilyRepository, ProductRepository productRepository) {
    this.productGroupRepository = productGroupRepository;
    this.productFamilyRepository = productFamilyRepository;
    this.productRepository = productRepository;
  }

  @Override
  public PagedModel<ProdGroupListDto> list(PageRequest pageReq, ProdGroupListFiltersDto filters) {
    Page<ProductGroup> groups;

    if (filters.isEmpty()) {
      groups = productGroupRepository.findAll(pageReq);
    } else {
      UUID id = CommonUtils.parseUUID(filters.getQ());
      groups = productGroupRepository.findByIdOrDescriptionContainingIgnoreCase(id, filters.getQ(), pageReq);
    }

    return new PagedModel<>(groups.map(productGroupMapper::toListDto));
  }

  @Override
  public ProdGroupDto getOne(UUID id) {
    ProductGroup group = getById(id);
    return productGroupMapper.toDto(group);
  }

  @Override
  public UUID create(ProdGroupDto dto) {
    ProductGroup ent = productGroupMapper.toEntity(dto);

    if (dto.getFamilyId() != null) {
      if (!productFamilyRepository.existsById(dto.getFamilyId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_FAMILY_NOT_FOUND);
      }
      ent.setFamily(productFamilyRepository.getReferenceById(dto.getFamilyId()));
    }

    return productGroupRepository.save(ent).getId();
  }

  @Override
  public void edit(UUID id, ProdGroupDto dto) {
    ProductGroup ent = getById(id);
    productGroupMapper.updateEntity(dto, ent);

    if (dto.getFamilyId() != null) {
      if (!productFamilyRepository.existsById(dto.getFamilyId())) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_FAMILY_NOT_FOUND);
      }
      ent.setFamily(productFamilyRepository.getReferenceById(dto.getFamilyId()));
    }

    productGroupRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!productGroupRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_GROUP_NOT_FOUND);
    }

    if (productRepository.existsByGroupId(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_GROUP_DELETE_DEPS);
    }

    productGroupRepository.deleteById(id);
  }

  private ProductGroup getById(UUID id) {
    return productGroupRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_GROUP_NOT_FOUND));
  }
}
