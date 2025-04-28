package app.restgourmet.api.procurement.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.procurement.enums.QuotationStatus;

public class Quotation {
  private PurchaseRequisition purchaseRequisition;

  private Vendor vendor;

  private LocalDateTime submittedDate;

  private Double totalAmount;

  private QuotationStatus status;

  private List<QuotationItem> items;
}
