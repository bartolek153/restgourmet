package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "business_partners")
public class BusinessPartner extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column
  private String email;

  @Column
  private String phone;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;
}
