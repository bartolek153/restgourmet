package app.restgourmet.api.procurement.repository;

import org.springframework.stereotype.Repository;

import app.restgourmet.api.procurement.models.PurchaseOrderParameters;
import app.restgourmet.api.shared.repository.BaseParameterRepository;

@Repository
public interface PurchaseOrderParametersRepository extends BaseParameterRepository<PurchaseOrderParameters> {
}
