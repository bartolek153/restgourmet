package app.restgourmet.api.shared.models.parameters;

import app.restgourmet.api.commondata.models.Currency;
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
@Table(name = "global_parameters")
public class GlobalParameters extends BaseParameter {
  private boolean initializedDb;

  private Currency defaultCurrency;

  private String companyLogoUrl;

  private boolean keepExchangeHistory;
}
