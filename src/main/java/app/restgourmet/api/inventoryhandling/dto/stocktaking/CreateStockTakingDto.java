package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockTakingDto {
  @NotNull private UUID warehouseId;
  @NotNull private String observation;
  @NotNull private List<StockTakingItemDto> items;
}
