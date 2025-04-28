package app.restgourmet.api.procurement.dto.purchaseorder;

import java.time.LocalDate;
import java.util.UUID;

import app.restgourmet.api.procurement.enums.PurchaseOrderStatus;
import lombok.Data;

@Data
public class PurchaseOrderListDto {
  private String orderNumber;
  private UUID vendorId;
  private LocalDate orderDate;
  private LocalDate expectedDeliveryDate;
  private PurchaseOrderStatus status;
  private Double totalAmount;
  private String currencyCode;
}
