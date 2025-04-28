package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.inventoryhandling.dto.inventory.InventoryDto;
import app.restgourmet.api.inventoryhandling.dto.inventory.InventoryListDto;
import app.restgourmet.api.inventoryhandling.dto.inventory.InventoryListFiltersDto;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;

public interface InventoryService {
  PagedModel<InventoryListDto> list(PageRequest pagReq, InventoryListFiltersDto filters);

  InventoryDto getOne(UUID id);

  boolean checkStockAvailability(Product prod, Double qty);

  boolean checkStockAvailability(Warehouse wh, Product prod, Double qty);

  boolean checkStockAvailability(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void increaseStock(Warehouse wh, Product prod, Double qty);

  void increaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void decreaseStock(Warehouse wh, Product prod, Double qty);

  void decreaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty);

  void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty, UnitMeasurement unit);

  void allocateStock(Product prod, Warehouse wh, Double qty);

  void allocateStock(Product prod, Warehouse wh, Double qty, UnitMeasurement unit);

  void deallocateStock(Product prod, Warehouse wh, Double qty);

  void deallocateStock(Product prod, Warehouse wh, Double qty, UnitMeasurement unit);
}
