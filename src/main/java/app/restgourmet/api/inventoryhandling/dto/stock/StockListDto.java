package app.restgourmet.api.inventoryhandling.dto.stock;

import java.util.UUID;

import app.restgourmet.api.inventoryhandling.enums.StockLevel;
import app.restgourmet.api.masterdata.dto.product.ProductSummaryDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseSummaryDto;
import lombok.Data;

@Data
public class StockListDto {
  private UUID id;
  private StockLevel level;
  private ProductSummaryDto product;
  private WarehouseSummaryDto warehouse;
  private Double qty;
  private UUID stockUnitId;
  private Double minQty;
  private UUID minQtyUnitId;
  private Double maxQty;
  private UUID maxQtyUnitId;
}
