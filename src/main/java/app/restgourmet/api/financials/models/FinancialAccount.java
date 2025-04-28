package app.restgourmet.api.financials.models;

import app.restgourmet.api.financials.enums.AccountType;
import app.restgourmet.api.shared.models.AuditableEntity;

public class FinancialAccount extends AuditableEntity {
  private String name;

  private AccountType type;

  private String institutionName;

  private String number;

  private String agency;

  private String currency;

  private Double initialBalance;
}
