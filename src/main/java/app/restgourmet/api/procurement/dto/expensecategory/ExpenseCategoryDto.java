package app.restgourmet.api.procurement.dto.expensecategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseCategoryDto {
  @NotNull
  private String name;

  private String description;
}
