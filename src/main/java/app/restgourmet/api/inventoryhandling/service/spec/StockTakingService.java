package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;

public interface StockTakingService {
  PagedModel<StockTakingListDto> list(PageRequest pageReq, StockTakingListFiltersDto filters);

  UUID create(CreateStockTakingDto dto, UUID userId);
}
