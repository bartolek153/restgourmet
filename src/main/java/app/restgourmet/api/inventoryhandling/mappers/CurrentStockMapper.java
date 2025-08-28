package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.inventoryhandling.dto.stock.InventoryDto;
import app.restgourmet.api.inventoryhandling.dto.stock.InventoryListDto;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrentStockMapper {
  InventoryDto toDto(CurrentStock ent);

  InventoryListDto toListDto(CurrentStock ent);
}
