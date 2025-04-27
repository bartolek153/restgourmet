package app.restgourmet.api.procurement.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
@Table(name = "purchase_requisition_items")
public class PurchaseRequisitionItem extends BaseEntity {
  @ManyToOne
  private PurchaseRequisition purchaseRequisition;

  @ManyToOne
  private Product product;

  private Double quantity;

  private UnitMeasurement unit;
}
