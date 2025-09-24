package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import app.restgourmet.api.masterdata.mappers.ProductMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductMapper.class)
public interface CurrentStockMapper {
  CurrentStockDto toDto(CurrentStock ent);

  @Mapping(target = "minQtyUnitId", source = "minQtyUnit.id")
  @Mapping(target = "maxQtyUnitId", source = "maxQtyUnit.id")
  @Mapping(target = "hasStock", ignore = true)
  CurrentStockListDto toListDto(CurrentStock ent);
}
