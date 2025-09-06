package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockTakingItemDto {
  @NotNull
  private UUID productId;
}
