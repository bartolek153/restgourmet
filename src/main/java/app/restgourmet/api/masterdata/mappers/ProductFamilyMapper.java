package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.family.ProdFamilyDto;
import app.restgourmet.api.masterdata.dto.family.ProdFamilyListDto;
import app.restgourmet.api.masterdata.models.ProductFamily;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductFamilyMapper {
  @Mapping(source = "category.id", target = "categoryId")
  ProdFamilyDto toDto(ProductFamily entity);

  ProdFamilyListDto toListDto(ProductFamily entity);

  ProductFamily toEntity(ProdFamilyDto dto);

  void updateEntity(ProdFamilyDto dto, @MappingTarget ProductFamily entity);
}
