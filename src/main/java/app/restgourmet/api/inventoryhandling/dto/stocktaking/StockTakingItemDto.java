package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.util.UUID;

import app.restgourmet.api.inventoryhandling.enums.StockTakingItemStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockTakingItemDto {
  private UUID id;

  @NotNull
  private UUID productId;

  private StockTakingItemStatus status;

  @Min(0)
  private Double countedQuantity;

  @Min(0)
  private Double systemQuantity;

  @Min(0)
  private Double difference;

  private boolean hasError;

  private String message;
}
