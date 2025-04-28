package app.restgourmet.api.procurement.dto.purchaseorder;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseOrderItemDto {
  @NotNull
  private UUID productId;

  private UUID unitId;
  
  private Double price;

  private Double orderedQuantity;
}
