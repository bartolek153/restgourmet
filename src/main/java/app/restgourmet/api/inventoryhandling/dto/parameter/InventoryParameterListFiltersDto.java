package app.restgourmet.api.inventoryhandling.dto.parameter;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class InventoryParameterListFiltersDto {
  private String q;
  private List<UUID> ids;
}
