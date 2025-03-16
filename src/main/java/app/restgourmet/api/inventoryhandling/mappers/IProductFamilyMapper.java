package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.ProdFamilyDto;
import app.restgourmet.api.inventoryhandling.models.ProductFamily;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IProductFamilyMapper {
  @Mapping(source = "category.id", target = "categoryId")
  ProdFamilyDto toDto(ProductFamily entity);

  ProductFamily toEntity(ProdFamilyDto dto);

  void updateEntity(ProdFamilyDto dto, @MappingTarget ProductFamily entity);
}
