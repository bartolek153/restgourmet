package app.restgourmet.api.masterdata.dto.address;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class AddressListFiltersDto {
  private String q = "";
  private List<UUID> ids;
}