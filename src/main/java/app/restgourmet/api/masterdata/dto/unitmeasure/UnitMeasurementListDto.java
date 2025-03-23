package app.restgourmet.api.masterdata.dto.unitmeasure;

import java.util.UUID;

import lombok.Data;

@Data
public class UnitMeasurementListDto {
  private UUID id;
  private String description;
  private String shortDescription;
  private UUID baseUnitId;
  private Double conversionFactor;
}
