package app.restgourmet.api.procurement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.procurement.dto.purchaseorder.CreatePurchaseOrderDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListFiltersDto;

public interface PurchaseOrderService {
  PagedModel<PurchaseOrderListDto> list(PageRequest pagReq, PurchaseOrderListFiltersDto filters);

  UUID create(CreatePurchaseOrderDto dto);
}
