package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class StockTakingDto {
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private List<StockTakingItemDto> items;
}
