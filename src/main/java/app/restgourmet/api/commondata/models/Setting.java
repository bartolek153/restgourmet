package app.restgourmet.api.commondata.models;

import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "settings")
public class Setting extends AuditableEntity {
  private boolean initializedDb;

  private Currency defaultCurrency;
  
  private String companyLogoUrl;

  private boolean keepExchangeHistory;
}
