package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.CurrentStockListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.EditCurrentStockDto;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import app.restgourmet.api.masterdata.mappers.ProductMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductMapper.class)
public interface CurrentStockMapper {
  @Mapping(target = "minQtyUnitId", source = "minQtyUnit.id")
  @Mapping(target = "maxQtyUnitId", source = "maxQtyUnit.id")
  CurrentStockDto toDto(CurrentStock ent);

  @Mapping(target = "minQtyUnitId", source = "minQtyUnit.id")
  @Mapping(target = "maxQtyUnitId", source = "maxQtyUnit.id")
  CurrentStockListDto toListDto(CurrentStock ent);

  void updateEntity(EditCurrentStockDto dto, @MappingTarget CurrentStock ent);
}
