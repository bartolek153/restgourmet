package app.restgourmet.api.masterdata.dto.family;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProdFamilyListFiltersDto {
  private String q;
  private UUID categoryId;
  private List<UUID> ids;
}