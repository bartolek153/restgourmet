package app.restgourmet.api.sales.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import app.restgourmet.api.masterdata.dto.address.AddressDto;
import app.restgourmet.api.sales.enums.PaymentMethod;
import lombok.Data;

@Data
public class SalesOrderDto {
    private UUID customerId;
    private UUID deliveryAddressId;
    private AddressDto deliveryAddress;
    private LocalDate deliveryDate;
    private PaymentMethod paymentMethod;
    private UUID warehouseId;
    private List<SalesOrderItemDto> items;
}
