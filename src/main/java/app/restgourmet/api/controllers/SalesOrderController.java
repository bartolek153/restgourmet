package app.restgourmet.api.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.sales.dto.SalesOrderDto;
import app.restgourmet.api.sales.dto.SalesOrderListDto;
import app.restgourmet.api.sales.dto.SalesOrderListFiltersDto;
import app.restgourmet.api.sales.enums.OrderStatus;
import app.restgourmet.api.sales.service.spec.SalesOrderService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales/orders")
@Tag(name = "Sales Order", description = "Sales order endpoints")
public class SalesOrderController {

  private final SalesOrderService salesOrderService;

  public SalesOrderController(SalesOrderService salesOrderService) {
    this.salesOrderService = salesOrderService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<SalesOrderListDto>> listSalesOrders(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "DESC") final Direction order,
      @RequestParam(defaultValue = "orderDate") final String sort,
      @ParameterObject final SalesOrderListFiltersDto filters) {
    return ResponseEntity.ok(salesOrderService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SalesOrderDto> getSalesOrder(@PathVariable UUID id) {
    return ResponseEntity.ok(salesOrderService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createSalesOrder(@RequestBody @Valid SalesOrderDto dto) {
    UUID id = salesOrderService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editSalesOrder(@PathVariable UUID id, @RequestBody @Valid SalesOrderDto dto) {
    salesOrderService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSalesOrder(@PathVariable UUID id) {
    salesOrderService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody OrderStatus status) {
    salesOrderService.updateStatus(id, status);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
