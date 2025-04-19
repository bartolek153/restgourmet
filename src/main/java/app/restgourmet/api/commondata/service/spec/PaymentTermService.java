package app.restgourmet.api.commondata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListFiltersDto;

public interface PaymentTermService {
  PagedModel<PaymentTermListDto> list(PageRequest pagReq, PaymentTermListFiltersDto filters);

  PaymentTermDto getOne(UUID id);

  UUID create(PaymentTermDto dto);

  void edit(UUID id, PaymentTermDto dto);

  void delete(UUID id);
}
