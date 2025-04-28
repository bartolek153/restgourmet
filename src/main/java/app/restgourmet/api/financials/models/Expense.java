package app.restgourmet.api.financials.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.commondata.models.PaymentTerm;
import app.restgourmet.api.financials.enums.ExpenseStatus;
import app.restgourmet.api.financials.enums.ExpenseType;
import app.restgourmet.api.procurement.models.Vendor;
import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "expenses")
public class Expense extends AuditableEntity {
  private String observation;

  @NotNull
  private LocalDateTime date;

  @NotNull
  private ExpenseStatus status;
  
  @NotNull
  private ExpenseType type;

  @ManyToOne
  private Vendor vendor;

  @OneToMany(fetch = FetchType.LAZY)
  private List<ExpenseItem> items;

  // paymentMethod	FK (optional)	Payment method used
  private Double totalAmount;
  
  private String receiptUrl;

  private PaymentTerm paymentTerm;

  private BudgetLine budgetLine;
}
