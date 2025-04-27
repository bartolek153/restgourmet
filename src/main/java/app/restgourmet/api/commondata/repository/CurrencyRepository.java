package app.restgourmet.api.commondata.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.commondata.models.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID>, JpaSpecificationExecutor<Currency> {
  boolean existsByCode(String code);
}
