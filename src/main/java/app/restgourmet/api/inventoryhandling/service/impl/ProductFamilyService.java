package app.restgourmet.api.inventoryhandling.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.inventoryhandling.dto.ListProdFamilyFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdFamilyDto;
import app.restgourmet.api.inventoryhandling.dto.ProdFamilyListDto;
import app.restgourmet.api.inventoryhandling.mappers.IProductFamilyMapper;
import app.restgourmet.api.inventoryhandling.models.ProductFamily;
import app.restgourmet.api.inventoryhandling.repository.ProductCategoryRepository;
import app.restgourmet.api.inventoryhandling.repository.ProductFamilyRepository;
import app.restgourmet.api.inventoryhandling.service.spec.IProductFamilyService;
import app.restgourmet.api.utils.CommonUtils;

@Service
public class ProductFamilyService implements IProductFamilyService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductFamilyRepository productFamilyRepository;

  @Autowired
  private IProductFamilyMapper productFamilyMapper;

  public ProductFamilyService(ProductFamilyRepository productFamilyRepository,
      ProductCategoryRepository productCategoryRepository) {
    this.productFamilyRepository = productFamilyRepository;
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public PagedModel<ProdFamilyListDto> list(PageRequest pageReq, ListProdFamilyFiltersDto filters) {
    Page<ProductFamily> families;

    if (filters.isEmpty()) {
      families = productFamilyRepository.findAll(pageReq);
    } else {
      UUID id = CommonUtils.parseUUID(filters.getQ());
      families = productFamilyRepository.findByIdOrDescriptionContainingIgnoreCase(id, filters.getQ(), pageReq);
    }

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
        throw new ResourceNotFoundException("Category not found");
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
        throw new ResourceNotFoundException("Category not found");
      }
      ent.setCategory(productCategoryRepository.getReferenceById(dto.getCategoryId()));
    }
    
    productFamilyRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!productFamilyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Family not found");
    }

    productFamilyRepository.deleteById(id);
  }

  private ProductFamily getById(UUID id) {
    return productFamilyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Family not found"));
  }
}
