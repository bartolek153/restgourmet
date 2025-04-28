package app.restgourmet.api.financials.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListFiltersDto;
import app.restgourmet.api.financials.mappers.ExpenseNatureMapper;
import app.restgourmet.api.financials.models.ExpenseNature;
import app.restgourmet.api.financials.repository.ExpenseNatureRepository;
import app.restgourmet.api.financials.repository.specifications.ExpenseNatureSpecification;
import app.restgourmet.api.financials.service.spec.ExpenseNatureService;
import app.restgourmet.api.masterdata.repository.BusinessPartnerRepository;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;

@Service
public class ExpenseNatureServiceImpl implements ExpenseNatureService {

  private final BusinessPartnerRepository businessPartnerRepository;
  private final ExpenseNatureRepository expenseCategoryRepository;
  private final ExpenseNatureMapper expenseCategoryMapper;

  public ExpenseNatureServiceImpl(
      ExpenseNatureRepository expenseCategoryRepository,
      ExpenseNatureMapper expenseCategoryMapper,
      BusinessPartnerRepository businessPartnerRepository) {
    this.expenseCategoryRepository = expenseCategoryRepository;
    this.expenseCategoryMapper = expenseCategoryMapper;
    this.businessPartnerRepository = businessPartnerRepository;
  }

  @Override
  public PagedModel<ExpenseNatureListDto> list(PageRequest pageReq, ExpenseNatureListFiltersDto filters) {
    Specification<ExpenseNature> spec = ExpenseNatureSpecification.filterBy(filters);
    Page<ExpenseNature> page = expenseCategoryRepository.findAll(spec, pageReq);
    return new PagedModel<>(page.map(expenseCategoryMapper::toListDto));
  }

  @Override
  public ExpenseNatureDto getOne(UUID id) {
    return expenseCategoryMapper.toDto(getById(id));
  }

  @Override
  @Transactional
  public UUID create(ExpenseNatureDto dto) {
    ExpenseNature expenseCategory = expenseCategoryMapper.toEntity(dto);
    expenseCategory = expenseCategoryRepository.save(expenseCategory);
    return expenseCategory.getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, ExpenseNatureDto dto) {
    ExpenseNature expenseCategory = getById(id);
    expenseCategoryMapper.updateEntity(dto, expenseCategory);
    expenseCategoryRepository.save(expenseCategory);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    expenseCategoryRepository.deleteById(id);
  }

  private ExpenseNature getById(UUID id) {
    return expenseCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.VENDOR_NOT_FOUND));
  }
}
