package app.restgourmet.api.inventoryhandling.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.inventoryhandling.dto.CreateProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.EditProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ListProdCategoryFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryListDto;
import app.restgourmet.api.inventoryhandling.mappers.IProductCategoryMapper;
import app.restgourmet.api.inventoryhandling.models.ProductCategory;
import app.restgourmet.api.inventoryhandling.repository.ProductCategoryRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.ProdCategorySpec;
import app.restgourmet.api.inventoryhandling.service.spec.IProductCategoryService;

@Service
public class ProductCategoryService implements IProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;
  
  @Autowired
  private IProductCategoryMapper productCategoryMapper;

  public ProductCategoryService(ProductCategoryRepository prodCategoryRepository) {
    productCategoryRepository = prodCategoryRepository;
  }

  @Override
  public PagedModel<ProdCategoryListDto> list(PageRequest pageReq, ListProdCategoryFiltersDto filters) {
    Specification<ProductCategory> spec = ProdCategorySpec.filterBy(filters);
    Page<ProdCategoryListDto> res = productCategoryRepository.findAll(spec, pageReq).map(productCategoryMapper::entityToListDto);
    return new PagedModel<>(res);

  }

  @Override
  public ProdCategoryDto getOne(UUID id) {
    ProductCategory cat = productCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    return productCategoryMapper.entityToDto(cat);
  }

  @Override
  public UUID create(CreateProdCategoryDto dto) {
    ProductCategory cat = productCategoryMapper.createDtoToEntity(dto);
    cat = productCategoryRepository.save(cat);
    return cat.getId();
  }

  @Override
  public void edit(UUID id, EditProdCategoryDto dto) {
    ProductCategory cat = productCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    productCategoryMapper.updateEntity(dto, cat);
    productCategoryRepository.save(cat);
  }

  @Override
  public void delete(UUID id) {
    // TODO: validate relationships 
    
    ProductCategory cat = productCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    productCategoryRepository.delete(cat);
  }
}