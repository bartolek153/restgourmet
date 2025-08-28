package app.restgourmet.api.inventoryhandling.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.Warehouse;

@Repository
public interface InventoryRepository extends JpaRepository<CurrentStock, UUID>, JpaSpecificationExecutor<CurrentStock> {
  boolean existsByWarehouseIdAndProductIdAndQuantityGreaterThan(UUID warehouseId, UUID productId, int quantity);

  Optional<CurrentStock> findByWarehouseAndProduct(Warehouse wh, Product prod);

  @Query("SELECT SUM(i.quantity) FROM Inventory i WHERE i.product.id = :productId")
  Double sumQuantityByProductId(UUID productId);
}
