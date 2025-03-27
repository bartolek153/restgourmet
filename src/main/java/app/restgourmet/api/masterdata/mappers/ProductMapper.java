package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.product.CreateProductDto;
import app.restgourmet.api.masterdata.dto.product.EditProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductListDto;
import app.restgourmet.api.masterdata.models.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
  ProductDto toDto(Product entity);

  @Mapping(target = "groupId", source = "group.id")
  ProductListDto toListDto(Product entity);
  
  Product toEntity(ProductDto dto);

  Product createDtoToEntity(CreateProductDto dto);

  void updateEntity(EditProductDto dto, @MappingTarget Product entity);
}
