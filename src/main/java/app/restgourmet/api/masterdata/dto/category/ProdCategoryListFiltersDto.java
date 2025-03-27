package app.restgourmet.api.masterdata.dto.category;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProdCategoryListFiltersDto {
  private String q = "";
  private List<UUID> ids;
}