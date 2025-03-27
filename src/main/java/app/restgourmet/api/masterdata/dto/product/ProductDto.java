package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductDto {
  private String sku;
  private String description;
  private UUID groupId;
  private String origin;
  private UUID inventoryUnitId;
  private UUID purchaseUnitId;
  private Double price;
}
