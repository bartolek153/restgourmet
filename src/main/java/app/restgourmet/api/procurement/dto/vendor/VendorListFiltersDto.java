package app.restgourmet.api.procurement.dto.vendor;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class VendorListFiltersDto {
  private String q;
  private List<UUID> ids;
}
