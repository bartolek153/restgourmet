package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.ProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryListDto;
import app.restgourmet.api.inventoryhandling.models.ProductCategory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IProductCategoryMapper {
  ProductCategory toEntity(ProdCategoryDto dto);

  void updateEntity(ProdCategoryDto dto, @MappingTarget ProductCategory entity);

  ProdCategoryListDto toListDto(ProductCategory entity);

  ProdCategoryDto toDto(ProductCategory entity);
}