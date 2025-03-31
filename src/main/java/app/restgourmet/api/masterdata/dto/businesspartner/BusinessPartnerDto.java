package app.restgourmet.api.masterdata.dto.businesspartner;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.BusinessPartnerStatus;
import app.restgourmet.api.masterdata.enums.BusinessPartnerType;
import app.restgourmet.api.masterdata.enums.TaxIdentificationNumberType;
import lombok.Data;

@Data
public class BusinessPartnerDto {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private BusinessPartnerType type;
  private BusinessPartnerStatus status;
  private TaxIdentificationNumberType tinType;
  private String taxIdentificationNumber;
  private String website;
  private boolean customer = false;
}
