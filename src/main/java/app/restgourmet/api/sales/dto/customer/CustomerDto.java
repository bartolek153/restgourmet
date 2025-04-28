package app.restgourmet.api.sales.dto.customer;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto {
  private UUID partnerId;
  
  private String email;

  @NotNull
  private UUID billingAddressId;
}
