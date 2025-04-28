package app.restgourmet.api.financials.dto.expensenature;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseNatureDto {
  @NotNull
  private String name;

  private String description;
}
