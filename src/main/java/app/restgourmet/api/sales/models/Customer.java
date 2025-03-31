package app.restgourmet.api.sales.models;

import java.util.UUID;

import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.masterdata.models.BusinessPartner;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends BusinessPartner {
  @ManyToOne(fetch = FetchType.LAZY)
  private Address billingAddress;

  public Customer(UUID parentId, Address billingAddress) {
    this.billingAddress = billingAddress;
  }
}
