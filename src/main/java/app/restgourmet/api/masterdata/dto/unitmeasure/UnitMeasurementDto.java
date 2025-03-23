package app.restgourmet.api.masterdata.dto.unitmeasure;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnitMeasurementDto {
  @NotNull
  private String description;
  private String shortDescription;

  @NotNull
  private UUID baseUnitId;
  private Double conversionFactor;
}
