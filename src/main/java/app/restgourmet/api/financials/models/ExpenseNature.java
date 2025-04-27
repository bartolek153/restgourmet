package app.restgourmet.api.financials.models;

import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "expense_categories")
public class ExpenseNature extends AuditableEntity {
  @NotNull
  private String code;

  private String name;

  private String description;

  private boolean requiresInvoice;

  private boolean isReimbursable;

  private Double budgetLimit;

  @OneToOne
  @JoinColumn(name = "parent_type_id")
  private ExpenseNature parentType;
}
