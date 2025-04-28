package app.restgourmet.api.financials.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.financials.dto.expense.CreateExpenseDto;
import app.restgourmet.api.financials.dto.expense.EditExpenseDto;
import app.restgourmet.api.financials.dto.expense.ExpenseDto;
import app.restgourmet.api.financials.dto.expense.ExpenseListDto;
import app.restgourmet.api.financials.dto.expense.ExpenseListFiltersDto;
import app.restgourmet.api.financials.mappers.ExpenseMapper;
import app.restgourmet.api.financials.models.Expense;
import app.restgourmet.api.financials.repository.ExpenseRepository;
import app.restgourmet.api.financials.repository.specifications.ExpenseSpecification;
import app.restgourmet.api.financials.service.spec.ExpenseService;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;
import jakarta.transaction.Transactional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;

  @Autowired
  private ExpenseMapper expenseMapper;

  public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  @Override
  public PagedModel<ExpenseListDto> list(PageRequest pagReq, ExpenseListFiltersDto filters) {
    Specification<Expense> spec = ExpenseSpecification.filterBy(filters);
    Page<Expense> exps = expenseRepository.findAll(spec, pagReq);
    return new PagedModel<>(exps.map(expenseMapper::toListDto));
  }

  @Override
  public ExpenseDto getOne(UUID id) {
    Expense exp = getById(id);
    return expenseMapper.toDto(exp);
  }

  @Override
  @Transactional
  public UUID create(CreateExpenseDto dto) {
    Expense exp = expenseMapper.toEntity(dto);
    return expenseRepository.save(exp).getId();
  }

  @Override
  @Transactional
  public void edit(UUID id, EditExpenseDto dto) {
    Expense exp = getById(id);
    expenseMapper.updateEntity(dto, exp);
    expenseRepository.save(exp);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    expenseRepository.deleteById(id);
  }

  private Expense getById(UUID id) {
    return expenseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.ADDRESS_DELETE_DEPS));
  }
}
