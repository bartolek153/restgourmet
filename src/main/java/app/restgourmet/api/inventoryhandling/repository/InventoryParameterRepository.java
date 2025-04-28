package app.restgourmet.api.inventoryhandling.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.shared.models.parameters.InventoryParameters;

@Repository
public interface InventoryParameterRepository extends JpaRepository<InventoryParameters, UUID> {
  Optional<InventoryParameters> findFirstByOrderByCreatedAtDesc();
}
