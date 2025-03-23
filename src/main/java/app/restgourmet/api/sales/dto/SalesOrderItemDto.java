package app.restgourmet.api.sales.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SalesOrderItemDto {
    private UUID productId;
    private Double quantity;
    private Double unitPrice;
    private Double taxRate;
}
