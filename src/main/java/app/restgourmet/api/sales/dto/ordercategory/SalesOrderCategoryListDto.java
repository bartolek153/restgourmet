package app.restgourmet.api.sales.dto.ordercategory;

import java.util.UUID;

import app.restgourmet.api.sales.enums.SalesOrderCategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalesOrderCategoryListDto {
  private UUID id;
  private String code;
  private String description;
  private SalesOrderCategoryType type;
  private boolean isDefault;
}
