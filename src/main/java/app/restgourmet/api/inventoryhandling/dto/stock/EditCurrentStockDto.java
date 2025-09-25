package app.restgourmet.api.inventoryhandling.dto.stock;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditCurrentStockDto {
  @Min(0) private Double minQty;
  @Min(0) private Double maxQty;
  @NotNull private UUID minQtyUnitId;
  @NotNull private UUID maxQtyUnitId;
}
