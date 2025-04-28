package app.restgourmet.api.sales.dto.ordercategory;

import app.restgourmet.api.sales.enums.SalesOrderCategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalesOrderCategoryDto {
  @NotNull private String code;
  @NotNull private String description;
  @NotNull private SalesOrderCategoryType type;
  private boolean isDefault;
}
