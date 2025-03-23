package app.restgourmet.api.masterdata.dto.family;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdFamilyDto {
  @NotNull
  private String description;
  private UUID categoryId;
}