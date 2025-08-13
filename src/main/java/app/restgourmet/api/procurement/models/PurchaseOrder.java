package app.restgourmet.api.procurement.models;

import java.time.LocalDate;
import java.util.List;

import app.restgourmet.api.commondata.models.Currency;
import app.restgourmet.api.commondata.models.PaymentTerm;
import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.procurement.enums.PurchaseOrderStatus;
import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder extends AuditableEntity {

  @NotNull
  @Column(unique = true)
  private String orderNumber;

  @NotNull
  @ManyToOne
  private Vendor vendor;

  @ManyToOne
  private PurchaseRequisition requisition;

  private LocalDate expectedDeliveryDate;

  private PurchaseOrderStatus status;

  private Address deliveryAddress;

  private PaymentTerm paymentTerms;

  @NotNull
  private Currency currency;

  @NotNull
  private Double totalAmount;

  private String notes;

  private List<PurchaseOrderItem> items;
}
