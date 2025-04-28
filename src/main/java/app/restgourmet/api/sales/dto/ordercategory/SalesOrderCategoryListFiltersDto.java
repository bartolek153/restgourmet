package app.restgourmet.api.sales.dto.ordercategory;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SalesOrderCategoryListFiltersDto {
  private String q;
  private List<UUID> ids;
}
