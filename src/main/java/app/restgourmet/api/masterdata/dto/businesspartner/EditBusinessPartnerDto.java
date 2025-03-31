package app.restgourmet.api.masterdata.dto.businesspartner;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.BusinessPartnerStatus;
import app.restgourmet.api.masterdata.enums.BusinessPartnerType;
import app.restgourmet.api.masterdata.enums.TaxIdentificationNumberType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditBusinessPartnerDto {
  private UUID id;

  @NotNull
  private String name;
  
  @Email
  private String email;

  private String phone;
  
  @NotNull
  private BusinessPartnerType type;
  
  @NotNull
  private BusinessPartnerStatus status;

  @NotNull
  private TaxIdentificationNumberType tinType;

  @NotNull
  private String taxIdentificationNumber;

  private String website;
}
