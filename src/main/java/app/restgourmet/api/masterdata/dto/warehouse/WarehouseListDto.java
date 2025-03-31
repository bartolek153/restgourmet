package app.restgourmet.api.masterdata.dto.warehouse;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.WarehouseStatus;
import lombok.Data;

@Data
public class WarehouseListDto {
  private UUID id;
  private String name;
  private UUID addressId;
  private WarehouseStatus status;
}
