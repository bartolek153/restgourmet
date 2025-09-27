package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.stock.StockDto;
import app.restgourmet.api.inventoryhandling.dto.stock.StockListDto;
import app.restgourmet.api.inventoryhandling.dto.stock.EditStockDto;
import app.restgourmet.api.inventoryhandling.models.Stock;
import app.restgourmet.api.masterdata.mappers.ProductMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductMapper.class)
public interface StockMapper {
  @Mapping(target = "minQtyUnitId", source = "minQtyUnit.id")
  @Mapping(target = "maxQtyUnitId", source = "maxQtyUnit.id")
  StockDto toDto(Stock ent);

  @Mapping(target = "minQtyUnitId", source = "minQtyUnit.id")
  @Mapping(target = "maxQtyUnitId", source = "maxQtyUnit.id")
  @Mapping(target = "stockUnitId", source = "product.stockUnit.id")
  StockListDto toListDto(Stock ent);

  void updateEntity(EditStockDto dto, @MappingTarget Stock ent);
}
