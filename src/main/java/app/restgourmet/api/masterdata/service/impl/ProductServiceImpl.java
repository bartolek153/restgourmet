package app.restgourmet.api.masterdata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.masterdata.dto.product.CreateProductDto;
import app.restgourmet.api.masterdata.dto.product.EditProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductListDto;
import app.restgourmet.api.masterdata.dto.product.ProductListFiltersDto;
import app.restgourmet.api.masterdata.enums.ProductStatus;
import app.restgourmet.api.masterdata.mappers.ProductMapper;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.repository.ProductGroupRepository;
import app.restgourmet.api.masterdata.repository.ProductRepository;
import app.restgourmet.api.masterdata.repository.UnitMeasurementRepository;
import app.restgourmet.api.masterdata.repository.specifications.ProductSpecification;
import app.restgourmet.api.masterdata.service.spec.ProductService;
import app.restgourmet.api.shared.exceptions.AppValidationException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductGroupRepository productGroupRepository;
  private final UnitMeasurementRepository unitMeasurementRepository;

  @Autowired
  private ProductMapper productMapper;

  public ProductServiceImpl(
      ProductRepository productRepository,
      ProductGroupRepository productGroupRepository,
      UnitMeasurementRepository unitMeasurementRepository) {
    this.productRepository = productRepository;
    this.productGroupRepository = productGroupRepository;
    this.unitMeasurementRepository = unitMeasurementRepository;
  }

  @Override
  public PagedModel<ProductListDto> list(PageRequest pageReq, ProductListFiltersDto filters) {
    Specification<Product> spec = ProductSpecification.filterBy(filters);
    Page<Product> page = productRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(productMapper::toListDto));
  }

  @Override
  public ProductDto getOne(UUID id) {
    Product product = getById(id);
    return productMapper.toDto(product);
  }

  @Override
  @Transactional
  public UUID create(CreateProductDto dto) {
    Product product = productMapper.createDtoToEntity(dto);
    validateInput(product, dto.getGroupId(), dto.getInventoryUnitId(), dto.getPurchaseUnitId());

    product.setStatus(ProductStatus.ACTIVE);

    if (product.getSku() != null) {
      if (productRepository.existsBySku(product.getSku())) {
        throw new AppValidationException("sku", AppConstants.ErrorMessages.PRODUCT_SKU_EXISTS);
      }
      product.setSku(product.getSku());
    }

    product = productRepository.save(product);

    return product.getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, EditProductDto dto) {
    Product product = getById(id);
    validateInput(product, dto.getGroupId(), dto.getInventoryUnitId(), dto.getPurchaseUnitId());

    if (dto.getSku() != null && !dto.getSku().equals(product.getSku())) {
      if (productRepository.existsBySku(dto.getSku())) {
        throw new AppValidationException("sku", AppConstants.ErrorMessages.PRODUCT_SKU_EXISTS);
      }
      product.setSku(dto.getSku());
    }

    productMapper.updateEntity(dto, product);
    productRepository.save(product);
  }

  @Override
  public void delete(UUID id) {
    Product product = getById(id);
    product.setDeleted(true);
    productRepository.save(product);
  }

  private Product getById(UUID id) {
    return productRepository.findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_NOT_FOUND));
  }

  // validateFields method is added to validate the fields of the product
  private void validateInput(Product prod, UUID groupId, UUID inventoryUnitId, UUID purchaseUnitId) {
    // required
    if (!unitMeasurementRepository.existsById(inventoryUnitId)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.UNIT_MEASUREMENT_NOT_FOUND);
    }
    prod.setInventoryUnit(unitMeasurementRepository.getReferenceById(inventoryUnitId));

    // optional
    if (groupId != null) {
      if (!productGroupRepository.existsById(groupId)) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_GROUP_NOT_FOUND);
      }
      prod.setGroup(productGroupRepository.getReferenceById(groupId));
    }

    // optional
    if (purchaseUnitId != null) {
      if (!unitMeasurementRepository.existsById(purchaseUnitId)) {
        throw new ResourceNotFoundException(AppConstants.ErrorMessages.UNIT_MEASUREMENT_NOT_FOUND);
      }
      prod.setPurchaseUnit(unitMeasurementRepository.getReferenceById(purchaseUnitId));
    }
  }
}
