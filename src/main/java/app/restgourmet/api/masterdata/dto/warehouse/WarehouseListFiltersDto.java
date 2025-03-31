package app.restgourmet.api.masterdata.dto.warehouse;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class WarehouseListFiltersDto {
  private String q = "";
  private List<UUID> ids;
}
