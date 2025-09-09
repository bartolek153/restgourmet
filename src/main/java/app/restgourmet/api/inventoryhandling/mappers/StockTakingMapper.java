package app.restgourmet.api.inventoryhandling.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingItemDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.models.StockTaking;
import app.restgourmet.api.inventoryhandling.models.StockTakingItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockTakingMapper {
  @Mapping(target = "createdById", source = "createdBy.id")
  StockTakingListDto toListDto(StockTaking ent);

  StockTakingDto toDto(StockTaking ent);

  StockTakingItemDto toItemDtoList(StockTakingItem ent);

  @Mapping(target = "items", ignore = true)
  StockTaking createDtoToEntity(CreateStockTakingDto dto);

  // @Named("mapItems")
  // static List<StockTakingItemDto> mapItems(List<StockTakingItem> items) {
  //   if (items != null) {
  //     List<StockTakingItemDto> res = new ArrayList<>();
  //     for (var i : items) {
  //       res.add(new StockTakingItemDto(
  //           i.getId(),
  //           i.getProduct().getId(),
  //           i.getCountedQuantity(),
  //           i.getSystemQuantity(),
  //           i.getDifference()));
  //     }
  //     return res;
  //   }
  //   return new ArrayList<>();
  // }
}
