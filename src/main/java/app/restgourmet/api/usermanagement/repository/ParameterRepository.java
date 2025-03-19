package app.restgourmet.api.usermanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.usermanagement.models.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
  Optional<Parameter> findByOptionKey(String optionKey);
}
