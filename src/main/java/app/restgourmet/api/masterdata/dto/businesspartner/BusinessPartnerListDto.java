package app.restgourmet.api.masterdata.dto.businesspartner;

import java.util.UUID;

import lombok.Data;

@Data
public class BusinessPartnerListDto {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private String addressCity;
  private String addressState;
}
