package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.ProductOrigin;
import app.restgourmet.api.masterdata.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductListFiltersDto {
  private String q;
  private UUID groupId;
  private UUID familyId;
  private UUID categoryId;
  private ProductOrigin origin;
  private UUID inventoryUnitId;
  private ProductStatus status;
  private Boolean deleted = false;
}
