package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.CreateProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.EditProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryDto;
import app.restgourmet.api.inventoryhandling.dto.ProdCategoryListDto;
import app.restgourmet.api.inventoryhandling.models.ProductCategory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IProductCategoryMapper {
  ProductCategory createDtoToEntity(CreateProdCategoryDto dto);

  void updateEntity(EditProdCategoryDto dto, @MappingTarget ProductCategory entity);

  ProdCategoryListDto entityToListDto(ProductCategory entity);

  ProdCategoryDto entityToDto(ProductCategory entity);
}