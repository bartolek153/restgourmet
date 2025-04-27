package app.restgourmet.api.financials.dto.expense;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseListFiltersDto {
  private String q;
  private List<UUID> ids;
}
