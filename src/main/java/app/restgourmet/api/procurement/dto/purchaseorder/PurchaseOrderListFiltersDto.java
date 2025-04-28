package app.restgourmet.api.procurement.dto.purchaseorder;

import java.time.LocalDate;
import java.util.UUID;

import app.restgourmet.api.procurement.enums.PurchaseOrderStatus;
import lombok.Data;

@Data
public class PurchaseOrderListFiltersDto {
  private String q;
  private LocalDate expectedDeliveryDate;
  private PurchaseOrderStatus status;
  private UUID vendorId;
  private UUID currencyId;
}
