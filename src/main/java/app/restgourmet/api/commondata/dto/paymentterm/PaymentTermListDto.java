package app.restgourmet.api.commondata.dto.paymentterm;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentTermListDto {
  private UUID id;
  private String description;
  private Double days;
}
