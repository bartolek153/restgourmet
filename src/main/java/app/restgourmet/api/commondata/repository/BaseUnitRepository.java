package app.restgourmet.api.commondata.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.commondata.models.BaseUnit;

@Repository
public interface BaseUnitRepository extends JpaRepository<BaseUnit, UUID> {
  Page<BaseUnit> findByIdOrDescriptionContainingIgnoreCase(UUID id, String description, Pageable pageable);
}
