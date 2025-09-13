package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.EditStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingItemDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;

public interface StockTakingService {
  PagedModel<StockTakingListDto> list(PageRequest pageReq, StockTakingListFiltersDto filters);

  List<StockTakingItemDto> getItems(UUID id);

  StockTakingDto getOne(UUID id);

  UUID create(CreateStockTakingDto dto, UUID userId);

  void edit(UUID id, EditStockTakingDto dto);

  void delete(UUID id);

  void process(UUID id);
}
