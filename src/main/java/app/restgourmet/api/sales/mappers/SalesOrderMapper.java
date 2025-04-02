package app.restgourmet.api.sales.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.sales.dto.SalesOrderDto;
import app.restgourmet.api.sales.dto.SalesOrderItemDto;
import app.restgourmet.api.sales.dto.SalesOrderListDto;
import app.restgourmet.api.sales.models.SalesOrder;
import app.restgourmet.api.sales.models.SalesOrderItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { CustomerMapper.class })
public interface SalesOrderMapper {

    // We'll handle the entity creation manually in the service
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "taxAmount", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "items", ignore = true)
    SalesOrder toEntity(SalesOrderDto dto);

    // We'll handle the entity update manually in the service
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    void updateEntity(SalesOrderDto dto, @MappingTarget SalesOrder entity);

    // @Mapping(target = "customerName", source = "customer.name")
    SalesOrderListDto toListDto(SalesOrder entity);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    SalesOrderDto toDto(SalesOrder entity);

    // We'll handle the entity creation manually in the service
    @Mapping(target = "salesOrder", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "taxAmount", ignore = true)
    @Mapping(target = "allocated", ignore = true)
    SalesOrderItem toEntity(SalesOrderItemDto dto);

    @Mapping(target = "productId", source = "product.id")
    SalesOrderItemDto toDto(SalesOrderItem entity);
}
