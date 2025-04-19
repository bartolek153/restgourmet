package app.restgourmet.api.financials.models;

import java.time.LocalDateTime;

import app.restgourmet.api.financials.enums.TransactionType;
import app.restgourmet.api.usermanagement.models.BaseEntity;

public class Transaction extends BaseEntity {
  private FinancialAccount account;

  private TransactionType type;

  private LocalDateTime date;

  private Double amount;

  private String description;

  
}
