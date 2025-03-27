package app.restgourmet.api.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.masterdata.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
  boolean existsByGroupId(UUID id);

  boolean existsBySku(String sku);

  Optional<Product> findByIdAndDeletedFalse(UUID id);
}
