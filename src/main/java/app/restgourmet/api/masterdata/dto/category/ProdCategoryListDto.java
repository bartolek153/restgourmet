package app.restgourmet.api.masterdata.dto.category;

import java.util.UUID;

import lombok.Data;

@Data
public class ProdCategoryListDto {
  private UUID id;
  private String description;
}