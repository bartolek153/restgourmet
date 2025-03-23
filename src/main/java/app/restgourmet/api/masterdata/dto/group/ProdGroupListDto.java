package app.restgourmet.api.masterdata.dto.group;

import java.util.UUID;

import lombok.Data;

@Data
public class ProdGroupListDto {
  private UUID id;
  private String description;
  private UUID familyId;
}