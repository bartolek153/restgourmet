package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.procurement.dto.purchaserequisition.CreatePurchaseRequisitionDto;
import app.restgourmet.api.procurement.dto.purchaserequisition.PurchaseRequisitionDto;
import app.restgourmet.api.procurement.dto.purchaserequisition.PurchaseRequisitionListDto;
import app.restgourmet.api.procurement.models.PurchaseRequisition;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseRequisitionMapper {
  PurchaseRequisitionDto toDto(PurchaseRequisition ent);

  PurchaseRequisitionListDto toListDto(PurchaseRequisition ent);

  PurchaseRequisition toEntity(CreatePurchaseRequisitionDto dto);

  void updateEntity(PurchaseRequisitionDto dto, @MappingTarget PurchaseRequisition ent);
}
