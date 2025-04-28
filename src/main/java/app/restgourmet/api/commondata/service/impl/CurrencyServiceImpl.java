package app.restgourmet.api.commondata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.dto.currency.CurrencyDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListFiltersDto;
import app.restgourmet.api.commondata.mappers.CurrencyMapper;
import app.restgourmet.api.commondata.models.Currency;
import app.restgourmet.api.commondata.repository.CurrencyRepository;
import app.restgourmet.api.commondata.repository.specifications.CurrencySpecification;
import app.restgourmet.api.commondata.service.spec.CurrencyService;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@Service
public class CurrencyServiceImpl implements CurrencyService {

  private final CurrencyRepository currencyRepository;

  @Autowired
  private CurrencyMapper currencyMapper;

  public CurrencyServiceImpl(
      CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  public PagedModel<CurrencyListDto> list(PageRequest pagReq, CurrencyListFiltersDto filters) {
    Specification<Currency> spec = CurrencySpecification.filterBy(filters);
    Page<Currency> pag = currencyRepository.findAll(spec, pagReq);
    return new PagedModel<>(pag.map(currencyMapper::toListDto));
  }

  public CurrencyDto getOne(UUID id) {
    return currencyMapper.toDto(getById(id));
  }

  public UUID create(CurrencyDto dto) {
    validateDuplicateCurrencyCode(dto);

    Currency ent = currencyMapper.toEntity(dto);
    return currencyRepository.save(ent).getId();
  }

  public void edit(UUID id, CurrencyDto dto) {
    Currency ent = getById(id);

    if (!dto.getCode().equals(ent.getCode())) {
      validateDuplicateCurrencyCode(dto);
    }

    currencyMapper.updateEntity(dto, ent);
    currencyRepository.save(ent);
  }

  public void delete(UUID id) {
    if (!currencyRepository.existsById(id)) {
      throw new ResourceNotFoundException(ErrorMessages.CURRENCY_NOT_FOUND);
    }

    currencyRepository.deleteById(id);
  }

  private Currency getById(UUID id) {
    return currencyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CURRENCY_NOT_FOUND));
  }

  private void validateDuplicateCurrencyCode(CurrencyDto dto) {
    if (currencyRepository.existsByCode(dto.getCode())) {
      throw new BadRequestException(ErrorMessages.CURRENCY_ALREADY_EXISTS);
    }
  }
}
