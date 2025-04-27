package app.restgourmet.api.financials.models;

import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

import app.restgourmet.api.financials.enums.BudgetScope;
import app.restgourmet.api.financials.enums.BudgetStatus;
import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget extends AuditableEntity {
  @NotNull
  private String name;

  @NotNull
  private BudgetScope scope;

  private UUID scopeReferenceId;

  @NotNull
  private LocalDate startDate;

  private LocalDate endDate;

  @NotNull
  private Double totalAmount;

  @NotNull
  private BudgetStatus status;

  private boolean enforceLimit; // if enabled, block expenses when limit is exceeded

  private Currency currency;

  @ManyToOne
  private CostCenter costCenter;
}
