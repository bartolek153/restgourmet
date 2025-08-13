package app.restgourmet.api.procurement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.procurement.models.PurchaseRequisition;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, UUID>, JpaSpecificationExecutor<PurchaseRequisition> {  
}
