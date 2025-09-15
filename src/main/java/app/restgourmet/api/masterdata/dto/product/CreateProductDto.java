package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.ProductOrigin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductDto {
  @NotNull private String description;
  @NotNull private UUID stockUnitId;
  @NotNull private ProductOrigin origin;

  private String sku;
  private UUID groupId;
  private UUID purchaseUnitId;
  private Double price;
}
