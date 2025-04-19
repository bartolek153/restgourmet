package app.restgourmet.api.procurement.dto.expensecategory;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseCategoryListFiltersDto {
  private String q;
  private List<UUID> ids;
}
