package app.restgourmet.api.financials.dto.expensenature;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseNatureListFiltersDto {
  private String q;
  private List<UUID> ids;
}
