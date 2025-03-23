package app.restgourmet.api.masterdata.dto.family;

import java.util.UUID;

import lombok.Data;

@Data
public class ProdFamilyListDto {
  private UUID id;
  private String description;
  private UUID categoryId;
}