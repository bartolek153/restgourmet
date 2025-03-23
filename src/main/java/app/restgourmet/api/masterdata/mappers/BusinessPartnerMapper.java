package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListDto;
import app.restgourmet.api.masterdata.models.BusinessPartner;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface BusinessPartnerMapper {
  @Mapping(target = "address", ignore = true)
  BusinessPartner toEntity(BusinessPartnerDto dto);

  @Mapping(target = "address", ignore = true)
  void updateEntity(BusinessPartnerDto dto, @MappingTarget BusinessPartner entity);

  @Mapping(target = "addressId", source = "address.id")
  BusinessPartnerDto toDto(BusinessPartner entity);

  @Mapping(target = "addressCity", source = "address.city")
  @Mapping(target = "addressState", source = "address.state")
  BusinessPartnerListDto toListDto(BusinessPartner entity);
}
