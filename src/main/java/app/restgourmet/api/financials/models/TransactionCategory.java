package app.restgourmet.api.financials.models;

import app.restgourmet.api.financials.enums.TransactionCategoryType;
import app.restgourmet.api.shared.models.AuditableEntity;

public class TransactionCategory extends AuditableEntity {
  private String name;

  private TransactionCategoryType type;

  private TransactionCategory parent;
}
