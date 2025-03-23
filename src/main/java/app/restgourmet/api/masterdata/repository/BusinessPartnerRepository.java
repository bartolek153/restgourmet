package app.restgourmet.api.masterdata.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.masterdata.models.BusinessPartner;

@Repository
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, UUID>, JpaSpecificationExecutor<BusinessPartner> {
}
