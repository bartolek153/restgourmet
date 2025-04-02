package app.restgourmet.api.sales.models;

import java.util.UUID;

import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.masterdata.models.BusinessPartner;
import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(unique = true)
  private BusinessPartner businessPartner;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Address billingAddress;
}
