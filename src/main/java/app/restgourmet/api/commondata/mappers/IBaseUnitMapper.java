package app.restgourmet.api.commondata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.commondata.dto.BaseUnitDto;
import app.restgourmet.api.commondata.dto.BaseUnitListDto;
import app.restgourmet.api.commondata.models.BaseUnit;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IBaseUnitMapper {
  BaseUnitDto toDto(BaseUnit ent);

  BaseUnit toEntity(BaseUnitDto dto);

  BaseUnitListDto toListDto(BaseUnit ent);

  void updateEntity(BaseUnitDto dto, @MappingTarget BaseUnit ent);
}
