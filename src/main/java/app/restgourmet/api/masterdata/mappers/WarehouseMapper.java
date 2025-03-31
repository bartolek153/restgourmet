package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.warehouse.WarehouseDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListDto;
import app.restgourmet.api.masterdata.models.Warehouse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {
  @Mapping(target = "addressId", source = "address.id")
  WarehouseDto toDto(Warehouse entity);

  @Mapping(target = "addressId", source = "address.id")
  WarehouseListDto toListDto(Warehouse entity);

  Warehouse toEntity(WarehouseDto dto);

  void updateEntity(WarehouseDto dto, @MappingTarget Warehouse entity);
}
