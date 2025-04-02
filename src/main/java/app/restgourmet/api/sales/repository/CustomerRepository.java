package app.restgourmet.api.sales.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.sales.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
  boolean existsByBusinessPartnerId(UUID id);

  Optional<Customer> findByBusinessPartnerId(UUID id);

  void deleteByBusinessPartnerId(UUID id);
}
