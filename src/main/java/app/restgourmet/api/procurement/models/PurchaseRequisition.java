package app.restgourmet.api.procurement.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.financials.models.CostCenter;
import app.restgourmet.api.procurement.enums.PurchaseRequsitionStatus;
import app.restgourmet.api.usermanagement.models.BaseEntity;
import app.restgourmet.api.usermanagement.models.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "purchase_requisitions")
public class PurchaseRequisition extends BaseEntity {
  private UserEntity requester;

  private LocalDateTime requestDate;

  private PurchaseRequsitionStatus status;

  private String observation;

  private CostCenter costCenter;

  @OneToMany
  private List<PurchaseRequisitionItem> items;
}
