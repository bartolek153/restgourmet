package app.restgourmet.api.masterdata.dto.unitmeasure;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UnitMeasurementListFiltersDto {
  private String q;
  private UUID baseUnitId;
  private List<UUID> ids;
}