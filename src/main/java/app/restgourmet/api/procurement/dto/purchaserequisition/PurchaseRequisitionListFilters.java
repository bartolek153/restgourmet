package app.restgourmet.api.procurement.dto.purchaserequisition;

import app.restgourmet.api.procurement.enums.PurchaseRequsitionStatus;
import lombok.Data;

@Data
public class PurchaseRequisitionListFilters {
  private String q;
  private PurchaseRequsitionStatus status;
}
