package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductDto {
  private String sku;
  private String description;
  private ProductStatus status;
  private UUID groupId;
  private String origin;
  private UUID inventoryUnitId;
  private UUID purchaseUnitId;
  private Double price;
}
