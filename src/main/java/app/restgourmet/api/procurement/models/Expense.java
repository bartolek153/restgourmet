package app.restgourmet.api.procurement.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
@Table(name = "expenses")
public class Expense extends AuditableEntity {
  private Double totalAmount;

  private String description;

  private LocalDateTime date;

  @ManyToOne
  private ExpenseCategory category;

  @ManyToOne
  private Vendor vendor;

  @OneToMany(fetch = FetchType.EAGER)
  private List<ExpenseItem> items;

  // paymentMethod	FK (optional)	Payment method used
  
  private String receiptUrl;
}
