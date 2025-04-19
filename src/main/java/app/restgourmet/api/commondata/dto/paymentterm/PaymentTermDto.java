package app.restgourmet.api.commondata.dto.paymentterm;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentTermDto {
  @NotNull
  private String description;

  @NotNull
  private Double days;
}
