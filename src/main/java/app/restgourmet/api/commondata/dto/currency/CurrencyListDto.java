package app.restgourmet.api.commondata.dto.currency;

import java.util.UUID;

import lombok.Data;

@Data
public class CurrencyListDto {
  private UUID id;
  private String code;
  private String description;
}
