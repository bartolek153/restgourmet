package app.restgourmet.api.sales.dto.order;

import java.time.LocalDate;
import java.util.UUID;

import app.restgourmet.api.sales.enums.OrderStatus;
import app.restgourmet.api.sales.enums.PaymentMethod;
import lombok.Data;

@Data
public class SalesOrderListDto {
  private UUID id;
  private String orderNumber;
  private String customerName;
  private OrderStatus status;
  private PaymentMethod paymentMethod;
  private LocalDate orderDate;
  private LocalDate deliveryDate;
  private Double totalAmount;
}
