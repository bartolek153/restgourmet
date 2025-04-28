package app.restgourmet.api.commondata.service.spec;


import app.restgourmet.api.commondata.dto.exchange.ExchangeRateDto;
import app.restgourmet.api.commondata.models.Currency;

public interface ExchangeRateService {
  ExchangeRateDto getRate(Currency from, Currency to);
}
