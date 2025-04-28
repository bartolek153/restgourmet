package app.restgourmet.api.commondata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.commondata.dto.currency.CurrencyDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListFiltersDto;

public interface CurrencyService {
  PagedModel<CurrencyListDto> list(PageRequest pagReq, CurrencyListFiltersDto filters);

  CurrencyDto getOne(UUID id);

  UUID create(CurrencyDto dto);

  void edit(UUID id, CurrencyDto dto);
  
  void delete(UUID id);
}
