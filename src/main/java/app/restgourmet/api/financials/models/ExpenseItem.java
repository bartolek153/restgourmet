package app.restgourmet.api.financials.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "expense_items")
public class ExpenseItem extends BaseEntity {
  private ExpenseNature nature;

  private Double totalAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id")
  private Expense expense;
}
