package app.restgourmet.api.commondata.dto.unit;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseUnitDto {
  @NotNull
  private String description;
  @NotNull
  private String shortDescription;
}
