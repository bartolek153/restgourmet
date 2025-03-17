package app.restgourmet.api.masterdata.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.masterdata.models.UnitMeasurement;

@Repository
public interface UnitMeasurementRepository extends JpaRepository<UnitMeasurement, UUID>, JpaSpecificationExecutor<UnitMeasurement> {
}
