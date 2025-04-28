package app.restgourmet.api.shared.models.parameters;

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
@Table(name = "expense_parameters")
public class ExpenseParameters extends BaseParameter {
  private boolean useApprovalWorkflow;

  private Double requiresApprovalOverAmount;

  private boolean trackBudgetUsage;

  private boolean canSubmitWithoutBudget;

  private Integer autoApproveAfterDays;

  private Integer reminderBeforeDueDays;
  
  private Integer allowedBackdateDays;
}
