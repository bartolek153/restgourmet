package app.restgourmet.api.procurement.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.procurement.enums.QuotationStatus;
import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests_for_quote")
public class RequestForQuote extends AuditableEntity {
  private PurchaseRequisition purchaseRequisition;

  private Vendor vendor;

  private LocalDateTime submittedDate;

  private Double totalAmount;

  private QuotationStatus status;

  private List<RequestForQuoteItem> items;
}
