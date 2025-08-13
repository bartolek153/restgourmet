package app.restgourmet.api.procurement.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListFiltersDto;
import app.restgourmet.api.procurement.service.spec.PurchaseOrderService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/purchases/orders/")
@Tag(name = "Purchase Order", description = "Purchase orders endpoints")
public class PurchaseOrderController {

  private final PurchaseOrderService purchaseOrderService;

  public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
    this.purchaseOrderService = purchaseOrderService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<PurchaseOrderListDto>> listOrders(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "DESC") final Direction order,
      @RequestParam(defaultValue = "orderDate") final String sort,
      @ParameterObject final PurchaseOrderListFiltersDto filters) {
    return ResponseEntity.ok(purchaseOrderService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }
}
