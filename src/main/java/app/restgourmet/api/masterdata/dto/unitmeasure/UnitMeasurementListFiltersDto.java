package app.restgourmet.api.masterdata.dto.unitmeasure;

import java.util.UUID;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class UnitMeasurementListFiltersDto {
  private String q;
  private UUID baseUnitId;

  public boolean isEmpty() {
    return !StringUtils.hasText(q) && baseUnitId == null;
  }
}