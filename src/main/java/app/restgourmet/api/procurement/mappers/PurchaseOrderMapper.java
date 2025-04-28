package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.procurement.dto.purchaseorder.CreatePurchaseOrderDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListDto;
import app.restgourmet.api.procurement.models.PurchaseOrder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseOrderMapper {
  
  // toDto()

  PurchaseOrderListDto toListDto(PurchaseOrder ent);

  PurchaseOrder toEntity(CreatePurchaseOrderDto dto);

  // updateEntity()
}
