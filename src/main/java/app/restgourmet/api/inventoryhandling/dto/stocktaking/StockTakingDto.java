package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.time.LocalDateTime;
import java.util.UUID;

import app.restgourmet.api.inventoryhandling.enums.StockTakingStatus;
import lombok.Data;

@Data
public class StockTakingDto {
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private UUID createdById;
  private UUID warehouseId;
  private StockTakingStatus status;
  private String observation;
}
