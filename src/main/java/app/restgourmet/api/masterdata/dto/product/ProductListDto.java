package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductListDto {
  private UUID id;
  private String sku;
  private String description;
  private UUID groupId;
  private String origin;
  private Double price;
}
