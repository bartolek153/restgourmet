package app.restgourmet.api.masterdata.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.masterdata.models.ProductGroup;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, UUID>, JpaSpecificationExecutor<ProductGroup> {
  Page<ProductGroup> findByIdOrDescriptionContainingIgnoreCase(UUID id, String description, Pageable pageable);

  boolean existsByFamilyId(UUID id);
}
