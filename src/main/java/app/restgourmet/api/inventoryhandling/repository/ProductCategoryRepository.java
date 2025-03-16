package app.restgourmet.api.inventoryhandling.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.inventoryhandling.models.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID>, JpaSpecificationExecutor<ProductCategory> {
}
