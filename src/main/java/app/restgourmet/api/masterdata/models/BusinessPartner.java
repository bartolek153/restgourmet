package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.masterdata.enums.BusinessPartnerStatus;
import app.restgourmet.api.masterdata.enums.BusinessPartnerType;
import app.restgourmet.api.masterdata.enums.TaxIdentificationNumberType;
import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Column;
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
@Table(name = "business_partners")
public class BusinessPartner extends BaseEntity {
  @Column(nullable = false)
  private String name;

  private String email;

  private String phone;

  private String website;

  private BusinessPartnerType type;

  private BusinessPartnerStatus status;

  private TaxIdentificationNumberType tinType;

  private String taxIdentificationNumber;

  private boolean active;
}
