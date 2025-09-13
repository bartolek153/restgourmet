package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class EditStockTakingDto {
  private UUID warehouseId;
  private String observation;
  private List<StockTakingItemDto> items;
}
