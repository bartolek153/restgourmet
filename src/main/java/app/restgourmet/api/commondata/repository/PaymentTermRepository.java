package app.restgourmet.api.commondata.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.commondata.models.PaymentTerm;

@Repository
public interface PaymentTermRepository extends JpaRepository<PaymentTerm, UUID>, JpaSpecificationExecutor<PaymentTerm> {
}
