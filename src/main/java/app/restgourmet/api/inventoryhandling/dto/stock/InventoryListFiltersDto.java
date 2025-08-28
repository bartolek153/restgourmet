package app.restgourmet.api.inventoryhandling.dto.stock;

import java.util.UUID;

import lombok.Data;

@Data
public class InventoryListFiltersDto {
  private String q;  
  private boolean withStock;
  private UUID productId;
  private UUID warehouseId;
}
