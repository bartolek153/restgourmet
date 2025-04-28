package app.restgourmet.api.sales.dto.order;

import java.util.UUID;

import lombok.Data;

@Data
public class SalesOrderItemDto {
  private UUID productId;
  private Double quantity;
  private UUID unitId;
  private Double unitPrice;
  private Double taxRate;
  private Double discount;
}
