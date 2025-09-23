package app.restgourmet.api.inventoryhandling.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.restgourmet.api.inventoryhandling.enums.StockTakingItemStatus;
import app.restgourmet.api.inventoryhandling.models.StockTakingItem;

public interface StockTakingItemRepository extends JpaRepository<StockTakingItem, UUID> {
  List<StockTakingItem> findByStockTakingId(UUID id);

  List<StockTakingItem> findByStockTakingIdAndStatus(UUID stid, StockTakingItemStatus status);
}
