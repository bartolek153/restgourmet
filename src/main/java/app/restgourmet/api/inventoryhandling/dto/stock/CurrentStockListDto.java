package app.restgourmet.api.inventoryhandling.dto.stock;

import java.util.UUID;

import app.restgourmet.api.masterdata.dto.product.ProductSummaryDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseSummaryDto;
import lombok.Data;

@Data
public class CurrentStockListDto {
  private UUID id;
  private ProductSummaryDto product;
  private WarehouseSummaryDto warehouse;
  private Double qty;
  private Double minQty;
  private UUID minQtyUnitId;
  private Double maxQty;
  private UUID maxQtyUnitId;
  
  @SuppressWarnings("unused")
  private boolean hasStock;

  public boolean getHasStock() {
    return qty > 0D;
  }
}
