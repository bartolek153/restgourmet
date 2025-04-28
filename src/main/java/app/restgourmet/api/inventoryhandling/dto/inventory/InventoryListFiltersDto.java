package app.restgourmet.api.inventoryhandling.dto.inventory;

import java.util.UUID;

import lombok.Data;

@Data
public class InventoryListFiltersDto {
  private String q;  
  private boolean withStock;
  private UUID productId;
  private UUID warehouseId;
}
