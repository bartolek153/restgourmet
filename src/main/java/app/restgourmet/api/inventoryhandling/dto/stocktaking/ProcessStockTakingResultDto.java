package app.restgourmet.api.inventoryhandling.dto.stocktaking;

import lombok.Data;

@Data
public class ProcessStockTakingResultDto {
  private String message;
  private boolean success;
}
