package app.restgourmet.api.procurement.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.shared.models.AuditableEntity;
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
@Table(name = "purchase_order_items")
public class PurchaseOrderItem extends AuditableEntity {

  @ManyToOne
  @NotNull
  private PurchaseOrder purchaseOrder;

  @NotNull
  private Product product;

  @NotNull
  private UnitMeasurement unit;

  @NotNull
  private Double price;

  @NotNull
  private Double orderedQuantity;

  @NotNull
  private Double totalPrice;

  private Double receivedQuantity;
}
