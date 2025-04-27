package app.restgourmet.api.financials.models;

import java.util.List;

import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "budget_lines")
public class BudgetLine extends AuditableEntity {
  private Budget budget;

  private ExpenseNature nature;

  private Double allocatedAmount;

  private Double usedAmount;

  private boolean locked;

  @OneToMany(mappedBy = "budgetLine")
  private List<Expense> expenses;
}
