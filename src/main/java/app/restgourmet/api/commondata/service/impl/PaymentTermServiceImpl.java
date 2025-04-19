package app.restgourmet.api.commondata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListFiltersDto;
import app.restgourmet.api.commondata.mappers.PaymentTermMapper;
import app.restgourmet.api.commondata.models.PaymentTerm;
import app.restgourmet.api.commondata.repository.PaymentTermRepository;
import app.restgourmet.api.commondata.repository.specifications.PaymentTermSpec;
import app.restgourmet.api.commondata.service.spec.PaymentTermService;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;

@Service
public class PaymentTermServiceImpl implements PaymentTermService {

  private final PaymentTermRepository paymentTermRepository;

  @Autowired
  private PaymentTermMapper paymentTermMapper;

  public PaymentTermServiceImpl(PaymentTermRepository paymentTermRepository) {
    this.paymentTermRepository = paymentTermRepository;
  }

  @Override
  public PagedModel<PaymentTermListDto> list(PageRequest pagReq, PaymentTermListFiltersDto filters) {
    Specification<PaymentTerm> spec = PaymentTermSpec.hasFilters(filters);
    Page<PaymentTerm> page = paymentTermRepository.findAll(spec, pagReq);
    return new PagedModel<>(page.map(paymentTermMapper::toListDto));
  }

  @Override
  public PaymentTermDto getOne(UUID id) {
    PaymentTerm unit = getById(id);
    return paymentTermMapper.toDto(unit);
  }

  @Override
  public UUID create(PaymentTermDto dto) {
    PaymentTerm ent = paymentTermMapper.toEntity(dto);
    return paymentTermRepository.save(ent).getId();
  }

  @Override
  public void edit(UUID id, PaymentTermDto dto) {
    PaymentTerm ent = getById(id);

    paymentTermMapper.updateEntity(dto, ent);
    paymentTermRepository.save(ent);
  }

  @Override
  public void delete(UUID id) {
    if (!paymentTermRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.BASE_UNIT_NOT_FOUND);
    }

    paymentTermRepository.deleteById(id);
  }

  private PaymentTerm getById(UUID id) {
    return paymentTermRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.PAYMENT_TERM_NOT_FOUND));
  }
}
