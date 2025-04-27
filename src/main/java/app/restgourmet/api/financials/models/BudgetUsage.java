package app.restgourmet.api.financials.models;

import java.time.LocalDate;

import app.restgourmet.api.usermanagement.models.BaseEntity;
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
@Table(name = "budget_usages")
public class BudgetUsage extends BaseEntity {
  private Budget budget;

  private BudgetLine line;

  private Expense expense;

  private Double amountUsage;

  private LocalDate usageDate;
}
