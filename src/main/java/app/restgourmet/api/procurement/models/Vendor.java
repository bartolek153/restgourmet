package app.restgourmet.api.procurement.models;

import app.restgourmet.api.commondata.models.PaymentTerm;
import app.restgourmet.api.masterdata.models.BusinessPartner;
import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "vendors")
public class Vendor extends AuditableEntity {
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(unique = true)
  private BusinessPartner businessPartner;
  
  @ManyToOne
  private PaymentTerm paymentTerm;
}
