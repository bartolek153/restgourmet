package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditProductDto {
  @NotNull private String description;
  @NotNull private UUID inventoryUnitId;
  @NotNull private String origin;
  @NotNull private ProductStatus status;
  
  private String sku;
  private UUID groupId;
  private UUID purchaseUnitId;
  private Double price;
}
