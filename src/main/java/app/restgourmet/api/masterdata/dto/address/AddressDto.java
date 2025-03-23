package app.restgourmet.api.masterdata.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
  @NotNull private String street;
  @NotNull private String number;
  @NotNull private String city;
  @NotNull private String state;
  @NotNull private String zipCode;
  @NotNull private String country;
  
  private String additionalInfo;
}
