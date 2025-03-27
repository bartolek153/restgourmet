package app.restgourmet.api.masterdata.dto.group;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProdGroupListFiltersDto {
  private String q;
  private UUID familyId;
  private List<UUID> ids;
}