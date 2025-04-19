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
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListFiltersDto;
import app.restgourmet.api.procurement.mappers.ExpenseCategoryMapper;
import app.restgourmet.api.procurement.models.ExpenseCategory;
import app.restgourmet.api.procurement.repository.ExpenseCategoryRepository;
import app.restgourmet.api.procurement.repository.specifications.ExpenseCategorySpec;
import app.restgourmet.api.procurement.service.spec.ExpenseCategoryService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

  private final BusinessPartnerRepository businessPartnerRepository;
  private final ExpenseCategoryRepository expenseCategoryRepository;
  private final ExpenseCategoryMapper expenseCategoryMapper;

  public ExpenseCategoryServiceImpl(
      ExpenseCategoryRepository expenseCategoryRepository,
      ExpenseCategoryMapper expenseCategoryMapper,
      BusinessPartnerRepository businessPartnerRepository) {
    this.expenseCategoryRepository = expenseCategoryRepository;
    this.expenseCategoryMapper = expenseCategoryMapper;
    this.businessPartnerRepository = businessPartnerRepository;
  }

  @Override
  public PagedModel<ExpenseCategoryListDto> list(PageRequest pageReq, ExpenseCategoryListFiltersDto filters) {
    Specification<ExpenseCategory> spec = ExpenseCategorySpec.filterBy(filters);
    Page<ExpenseCategory> page = expenseCategoryRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(expenseCategoryMapper::toListDto));
  }

  @Override
  public ExpenseCategoryDto getOne(UUID id) {
    return expenseCategoryMapper.toDto(getById(id));
  }

  @Override
  @Transactional
  public UUID create(ExpenseCategoryDto dto) {
    ExpenseCategory expenseCategory = expenseCategoryMapper.toEntity(dto);
    expenseCategory = expenseCategoryRepository.save(expenseCategory);
    return expenseCategory.getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, ExpenseCategoryDto dto) {
    ExpenseCategory expenseCategory = getById(id);
    expenseCategoryMapper.updateEntity(dto, expenseCategory);
    expenseCategoryRepository.save(expenseCategory);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    expenseCategoryRepository.deleteById(id);
  }

  private ExpenseCategory getById(UUID id) {
    return expenseCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.VENDOR_NOT_FOUND));
  }
}
