package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.time.LocalDateTime;
import java.util.UUID;

import app.restgourmet.api.inventoryhandling.enums.StockTakingStatus;
import app.restgourmet.api.shared.dto.CreatedByDataDto;
import lombok.Data;

@Data
public class StockTakingListDto {
  private UUID id;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private CreatedByDataDto createdBy;
  private StockTakingStatus status;
  private String observation;
}
