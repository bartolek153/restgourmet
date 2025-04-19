package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.procurement.dto.vendor.VendorDto;
import app.restgourmet.api.procurement.dto.vendor.VendorListDto;
import app.restgourmet.api.procurement.models.Vendor;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VendorMapper {
  VendorDto toDto(Vendor ent);

  VendorListDto toListDto(Vendor ent);
  
  Vendor toEntity(VendorDto dto);

  void updateEntity(VendorDto dto, @MappingTarget Vendor ent);
}
