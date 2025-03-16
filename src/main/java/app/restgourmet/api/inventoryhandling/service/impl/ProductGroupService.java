package app.restgourmet.api.inventoryhandling.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.inventoryhandling.dto.ListProdGroupFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupListDto;
import app.restgourmet.api.inventoryhandling.mappers.IProductGroupMapper;
import app.restgourmet.api.inventoryhandling.models.ProductGroup;
import app.restgourmet.api.inventoryhandling.repository.ProductFamilyRepository;
import app.restgourmet.api.inventoryhandling.repository.ProductGroupRepository;
import app.restgourmet.api.inventoryhandling.service.spec.IProductGroupService;
import app.restgourmet.api.utils.CommonUtils;

@Service
public class ProductGroupService implements IProductGroupService {

  private final ProductFamilyRepository productFamilyRepository;
  private final ProductGroupRepository productGroupRepository;

  @Autowired
  private IProductGroupMapper productGroupMapper;

  public ProductGroupService(ProductGroupRepository productGroupRepository,
      ProductFamilyRepository productFamilyRepository) {
    this.productGroupRepository = productGroupRepository;
    this.productFamilyRepository = productFamilyRepository;
  }

  @Override
  public PagedModel<ProdGroupListDto> list(PageRequest pageReq, ListProdGroupFiltersDto filters) {
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
        throw new ResourceNotFoundException("Family not found");
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
        throw new ResourceNotFoundException("Family not found");
      }
      ent.setFamily(productFamilyRepository.getReferenceById(dto.getFamilyId()));
    }
    
    productGroupRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!productGroupRepository.existsById(id)) {
      throw new ResourceNotFoundException("Group not found");
    }

    productGroupRepository.deleteById(id);
  }

  private ProductGroup getById(UUID id) {
    return productGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
  }
}
