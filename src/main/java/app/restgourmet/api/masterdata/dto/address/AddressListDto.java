package app.restgourmet.api.masterdata.dto.address;

import java.util.UUID;

import lombok.Data;

@Data
public class AddressListDto {
  private UUID id;
  private String street;
  private String number;
  private String zipCode;
  private String city;
  private String state;
}