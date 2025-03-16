package app.restgourmet.api.inventoryhandling.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.inventoryhandling.models.ProductFamily;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamily, UUID> {
  Page<ProductFamily> findByIdOrDescriptionContainingIgnoreCase(UUID id, String description, Pageable pageable);

  Page<ProductFamily> findByIdOrDescriptionContainingIgnoreCase(List<UUID> ids, String description, Pageable pageable);
}
