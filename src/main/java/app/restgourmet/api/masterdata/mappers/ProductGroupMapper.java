package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.group.ProdGroupDto;
import app.restgourmet.api.masterdata.dto.group.ProdGroupListDto;
import app.restgourmet.api.masterdata.models.ProductGroup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductGroupMapper {
  @Mapping(source = "family.id", target = "familyId")
  ProdGroupDto toDto(ProductGroup entity);

  ProdGroupListDto toListDto(ProductGroup entity);

  ProductGroup toEntity(ProdGroupDto dto);

  void updateEntity(ProdGroupDto dto, @MappingTarget ProductGroup entity);
}
