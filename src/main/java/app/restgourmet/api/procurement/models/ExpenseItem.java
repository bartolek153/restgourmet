package app.restgourmet.api.procurement.models;

import java.util.List;

import app.restgourmet.api.masterdata.models.Product;
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
  private Product item;

  private Double quantity;

  private Double price;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "expense_id")
  private List<Expense> expense;
}
