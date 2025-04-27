package app.restgourmet.api.commondata.dto.exchange;

import java.time.LocalDateTime;

import app.restgourmet.api.commondata.models.Currency;
import lombok.Data;

@Data
public class ExchangeRateDto {
  private Currency from;
  private Currency to;
  private Double rate;
}
