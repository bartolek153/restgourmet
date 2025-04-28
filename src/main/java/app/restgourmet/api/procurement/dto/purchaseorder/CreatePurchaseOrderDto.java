package app.restgourmet.api.procurement.dto.purchaseorder;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePurchaseOrderDto {

  @NotNull
  private UUID vendorId;
  
  private UUID requisitionId;

  @NotNull
  private LocalDate expectedDeliveryDate;

  private UUID currencyId;

  private UUID paymentTermsId;

  private String notes;
}
