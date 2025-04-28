package app.restgourmet.api.sales.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.sales.models.SalesOrderCategory;

@Repository
public interface SalesOrderCategoryRepository
    extends JpaRepository<SalesOrderCategory, UUID>, JpaSpecificationExecutor<SalesOrderCategory> {
      boolean existsByCode(String code);
}
