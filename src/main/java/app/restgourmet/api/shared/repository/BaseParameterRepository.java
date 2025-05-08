package app.restgourmet.api.shared.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.shared.models.parameters.BaseParameter;

@Repository
public interface BaseParameterRepository<T extends BaseParameter> extends JpaRepository<T, UUID> {
  Optional<T> findFirstByIsActiveTrue();
}
