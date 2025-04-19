package app.restgourmet.api.procurement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.procurement.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID>, JpaSpecificationExecutor<Vendor> {
  boolean existsByBusinessPartnerId(UUID id);

  Optional<Vendor> findByBusinessPartnerId(UUID id);

  void deleteByBusinessPartnerId(UUID id);
}
