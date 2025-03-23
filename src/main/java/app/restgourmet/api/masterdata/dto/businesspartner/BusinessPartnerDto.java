package app.restgourmet.api.masterdata.dto.businesspartner;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessPartnerDto {
  private UUID id;
  @NotNull private String name;
  @Email private String email;
  private String phone;
  private UUID addressId;
  private boolean customer = false;
}
