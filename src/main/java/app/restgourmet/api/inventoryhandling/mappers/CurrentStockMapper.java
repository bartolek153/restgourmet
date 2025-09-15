package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrentStockMapper {
  CurrentStockDto toDto(CurrentStock ent);

  CurrentStockListDto toListDto(CurrentStock ent);
}
