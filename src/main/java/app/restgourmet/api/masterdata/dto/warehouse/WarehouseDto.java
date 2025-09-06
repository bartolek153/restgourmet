package app.restgourmet.api.masterdata.dto.warehouse;

import java.util.UUID;

import app.restgourmet.api.masterdata.enums.WarehouseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseDto {
  @NotNull private String name;
  
  private UUID addressId;
  
  private WarehouseStatus status;
}
