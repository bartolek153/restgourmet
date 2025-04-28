package app.restgourmet.api.sales.dto.order;

import java.time.LocalDate;
import java.util.UUID;

import app.restgourmet.api.sales.enums.OrderStatus;
import app.restgourmet.api.sales.enums.PaymentMethod;
import lombok.Data;

@Data
public class SalesOrderListFiltersDto {
  private String q;
  private String orderNumber;
  private UUID customerId;
  private String customerName;
  private OrderStatus status;
  private PaymentMethod paymentMethod;
  private LocalDate orderDateFrom;
  private LocalDate orderDateTo;
  private LocalDate deliveryDateFrom;
  private LocalDate deliveryDateTo;
  private UUID warehouseId;
}
