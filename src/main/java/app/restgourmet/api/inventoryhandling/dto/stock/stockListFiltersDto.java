package app.restgourmet.api.inventoryhandling.dto.stock;

import java.util.UUID;

import lombok.Data;

@Data
public class stockListFiltersDto {
  private String q;  
  private boolean hasStock;
  private UUID productId;
  private UUID warehouseId;
}
