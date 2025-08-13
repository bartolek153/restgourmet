package app.restgourmet.api.procurement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.procurement.dto.purchaseorder.CreatePurchaseOrderDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderDto;
import app.restgourmet.api.procurement.dto.purchaserequisition.PurchaseRequisitionListFilters;
import app.restgourmet.api.procurement.models.PurchaseRequisition;

public interface PurchaseRequisitionService {
  PagedModel<PurchaseRequisition> list(PageRequest pagReq, PurchaseRequisitionListFilters filters);

  PurchaseOrderDto getOne(UUID id);

  UUID create(CreatePurchaseOrderDto dto);

  void edit(UUID id, PurchaseOrderDto dto);

  void delete(UUID id);
}
