package app.restgourmet.api.masterdata.dto.unitmeasure;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnitMeasurementDto {
  @NotNull private String description;
  @NotNull private UUID baseUnitId;

  private String shortDescription;
  private Double conversionFactor;
}
