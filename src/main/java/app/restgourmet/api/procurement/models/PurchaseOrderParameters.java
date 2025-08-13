package app.restgourmet.api.procurement.models;

import app.restgourmet.api.shared.models.parameters.BaseParameter;
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
@Table(name = "purchase_order_parameters")
public class PurchaseOrderParameters extends BaseParameter {
  private boolean requireApproval;

  private String orderPrefix;
}
