package app.restgourmet.api.inventoryhandling.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdGroupDto {
  @NotNull
  private String description;
  private UUID familyId;
}