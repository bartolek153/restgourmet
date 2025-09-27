package app.restgourmet.api.inventoryhandling.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.inventoryhandling.models.Stock;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.Warehouse;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID>, JpaSpecificationExecutor<Stock> {
  boolean existsByWarehouseIdAndProductIdAndQtyGreaterThan(UUID warehouseId, UUID productId, int qty);

  Optional<Stock> findByWarehouseAndProduct(Warehouse wh, Product prod);

  @Query("SELECT SUM(i.qty) FROM Stock i WHERE i.product.id = :productId")
  Double sumQuantityByProductId(UUID productId);
}
