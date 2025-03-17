package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.UnitMeasurementDto;
import app.restgourmet.api.masterdata.dto.UnitMeasurementListDto;
import app.restgourmet.api.masterdata.models.UnitMeasurement;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUnitMeasurementMapper {
  @Mapping(source = "baseUnit.id", target = "baseUnitId")
  UnitMeasurementDto toDto(UnitMeasurement ent);
  
  UnitMeasurementListDto toListDto(UnitMeasurement ent);

  UnitMeasurement toEntity(UnitMeasurementDto dto);

  void updateEntity(UnitMeasurementDto dto, @MappingTarget UnitMeasurement ent);
}
