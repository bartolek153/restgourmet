package app.restgourmet.api.sales.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryDto;
import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListDto;
import app.restgourmet.api.sales.models.SalesOrderCategory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalesOrderCategoryMapper {
  SalesOrderCategoryDto toDto(SalesOrderCategory ent);

  SalesOrderCategoryListDto toListDto(SalesOrderCategory ent);

  SalesOrderCategory toEntity(SalesOrderCategoryDto dto);

  void updateEntity(SalesOrderCategoryDto dto, @MappingTarget SalesOrderCategory ent);
}
