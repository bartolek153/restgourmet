package app.restgourmet.api.masterdata.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductSummaryDto {
  private UUID id;
  private String description;
}
