package app.restgourmet.api.masterdata.dto.warehouse;

import java.util.UUID;

import lombok.Data;

@Data
public class WarehouseSummaryDto {
  private UUID id;
  private String name;
}
