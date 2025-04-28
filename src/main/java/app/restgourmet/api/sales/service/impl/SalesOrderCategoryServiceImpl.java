package app.restgourmet.api.sales.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryDto;
import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListDto;
import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListFiltersDto;
import app.restgourmet.api.sales.mappers.SalesOrderCategoryMapper;
import app.restgourmet.api.sales.models.SalesOrderCategory;
import app.restgourmet.api.sales.repository.SalesOrderCategoryRepository;
import app.restgourmet.api.sales.repository.specifications.SalesOrderCategorySpecification;
import app.restgourmet.api.sales.service.spec.SalesOrderCategoryService;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@Service
public class SalesOrderCategoryServiceImpl implements SalesOrderCategoryService {

  private final SalesOrderCategoryRepository salesOrderCategoryRepository;
  private final SalesOrderCategoryMapper salesOrderCategoryMapper;

  public SalesOrderCategoryServiceImpl(
      SalesOrderCategoryRepository salesOrderCategoryRepository,
      SalesOrderCategoryMapper salesOrderCategoryMapper) {
    this.salesOrderCategoryRepository = salesOrderCategoryRepository;
    this.salesOrderCategoryMapper = salesOrderCategoryMapper;
  }

  @Override
  public PagedModel<SalesOrderCategoryListDto> list(PageRequest pageReq, SalesOrderCategoryListFiltersDto filters) {
    Specification<SalesOrderCategory> spec = SalesOrderCategorySpecification.filterBy(filters);
    Page<SalesOrderCategory> page = salesOrderCategoryRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(salesOrderCategoryMapper::toListDto));
  }

  @Override
  public SalesOrderCategoryDto getOne(UUID id) {
    return salesOrderCategoryMapper.toDto(getById(id));
  }

  @Override
  @Transactional
  public UUID create(SalesOrderCategoryDto dto) {
    if (salesOrderCategoryRepository.existsByCode(dto.getCode())) {
      throw new BadRequestException(ErrorMessages.SALES_ORDER_CATEGORY_DUPLICATE_CODE);
    }

    SalesOrderCategory salesOrderCategory = salesOrderCategoryMapper.toEntity(dto);

    return salesOrderCategoryRepository.save(salesOrderCategory).getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, SalesOrderCategoryDto dto) {
    SalesOrderCategory salesOrderCategory = getById(id);
    
    if (!dto.getCode().equals(salesOrderCategory.getCode())) {
      if (salesOrderCategoryRepository.existsByCode(dto.getCode())) {
        throw new BadRequestException(ErrorMessages.SALES_ORDER_CATEGORY_DUPLICATE_CODE);
      }
    }
    salesOrderCategoryMapper.updateEntity(dto, salesOrderCategory);
    salesOrderCategoryRepository.save(salesOrderCategory);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    salesOrderCategoryRepository.deleteById(id);
  }

  private SalesOrderCategory getById(UUID id) {
    return salesOrderCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SALES_ORDER_CATEGORY_NOT_FOUND));
  }
}
