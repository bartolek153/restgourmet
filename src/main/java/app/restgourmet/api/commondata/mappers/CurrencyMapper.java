package app.restgourmet.api.commondata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.commondata.dto.currency.CurrencyDto;
import app.restgourmet.api.commondata.dto.currency.CurrencyListDto;
import app.restgourmet.api.commondata.models.Currency;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyMapper {
  CurrencyDto toDto(Currency ent);

  CurrencyListDto toListDto(Currency ent);

  Currency toEntity(CurrencyDto dto);

  void updateEntity(CurrencyDto dto, @MappingTarget Currency ent);
}
