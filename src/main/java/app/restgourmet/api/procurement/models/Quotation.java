package app.restgourmet.api.procurement.models;

import java.time.LocalDateTime;

import app.restgourmet.api.procurement.enums.QuotationStatus;

public class Quotation {
  private PurchaseRequisition purchaseRequisition;

  private Vendor vendor;

  private LocalDateTime submittedDate;

  private Double totalAmount;

  private QuotationStatus status;
}
