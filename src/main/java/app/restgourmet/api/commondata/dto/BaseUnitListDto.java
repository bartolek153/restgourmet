package app.restgourmet.api.commondata.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseUnitListDto {
  private UUID id;
  @NotNull private String description;
  @NotNull private String shortDescription;
}
