package app.restgourmet.api.commondata.dto.exchange;

import app.restgourmet.api.commondata.models.Currency;
import lombok.Data;

@Data
public class OpenExchangeRatesResponseDto {
  private String disclaimer;
  private String license;

  private Meta meta;
  private Double response;

  @Data
  public class Meta {
    private Integer timestamp;
    private Double rate;
  }

  public ExchangeRateDto toExchangeRateDto(Currency from, Currency to) {
    ExchangeRateDto rt = new ExchangeRateDto();
    rt.setFrom(from);
    rt.setTo(to);
    rt.setRate(this.meta.rate);
    return rt;
  }
}
