package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.stock.EditCurrentStockDto;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;

public interface CurrentStockService {
  PagedModel<CurrentStockListDto> list(PageRequest pagReq, CurrentStockListFiltersDto filters);

  CurrentStockDto getOne(UUID id);

  void edit(UUID id, EditCurrentStockDto dto);

  boolean checkStockAvailability(Product prod, Double qty);

  boolean checkStockAvailability(Warehouse wh, Product prod, Double qty);

  boolean checkStockAvailability(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void adjustStock(Warehouse wh, Product prod, Double qty);

  void increaseStock(Warehouse wh, Product prod, Double qty);

  void increaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void decreaseStock(Warehouse wh, Product prod, Double qty);

  void decreaseStock(Warehouse wh, Product prod, Double qty, UnitMeasurement unit);

  void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty);

  void moveStock(Product prod, Warehouse fromWh, Warehouse toWh, Double qty, UnitMeasurement unit);
}
