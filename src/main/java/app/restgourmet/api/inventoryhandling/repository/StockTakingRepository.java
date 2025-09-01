package app.restgourmet.api.inventoryhandling.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.inventoryhandling.models.StockTaking;

@Repository
public interface StockTakingRepository extends JpaRepository<StockTaking, UUID>, JpaSpecificationExecutor<StockTaking> {
}
