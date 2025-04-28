package app.restgourmet.api.commondata.dto.currency;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurrencyDto {
  @NotNull
  private String code;
  private String description;
}
