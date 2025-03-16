package app.restgourmet.api.inventoryhandling.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.inventoryhandling.dto.ProdGroupDto;
import app.restgourmet.api.inventoryhandling.dto.ProdGroupListDto;
import app.restgourmet.api.inventoryhandling.models.ProductGroup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IProductGroupMapper {
  @Mapping(source = "family.id", target = "familyId")
  ProdGroupDto toDto(ProductGroup entity);

  ProdGroupListDto toListDto(ProductGroup entity);

  ProductGroup toEntity(ProdGroupDto dto);

  void updateEntity(ProdGroupDto dto, @MappingTarget ProductGroup entity);
}
