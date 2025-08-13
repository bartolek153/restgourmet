package app.restgourmet.api.inventoryhandling.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import app.restgourmet.api.shared.models.parameters.InventoryParameters;
import app.restgourmet.api.shared.repository.BaseParameterRepository;

@Repository
public interface InventoryParametersRepository extends BaseParameterRepository<InventoryParameters> {
  Optional<InventoryParameters> findFirstByOrderByCreatedAtDesc();
}
