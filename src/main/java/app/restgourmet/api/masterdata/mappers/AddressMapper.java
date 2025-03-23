package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.address.AddressDto;
import app.restgourmet.api.masterdata.dto.address.AddressListDto;
import app.restgourmet.api.masterdata.models.Address;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
  Address toEntity(AddressDto dto);

  void updateEntity(AddressDto dto, @MappingTarget Address entity);

  AddressDto toDto(Address entity);

  AddressListDto toListDto(Address entity);
}
