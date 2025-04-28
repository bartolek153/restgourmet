package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.inventoryhandling.dto.inventory.InventoryDto;
import app.restgourmet.api.inventoryhandling.dto.inventory.InventoryListDto;
import app.restgourmet.api.inventoryhandling.models.Inventory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {
  InventoryDto toDto(Inventory ent);

  InventoryListDto toListDto(Inventory ent);
}
