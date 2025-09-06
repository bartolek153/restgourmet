package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.models.StockTaking;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockTakingMapper {
    StockTakingListDto toListDto(StockTaking ent);

    StockTaking createDtoToEntity(CreateStockTakingDto dto);
}
