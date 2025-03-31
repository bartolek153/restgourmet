package app.restgourmet.api.masterdata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListDto;
import app.restgourmet.api.masterdata.dto.businesspartner.CreateBusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.EditBusinessPartnerDto;
import app.restgourmet.api.masterdata.models.BusinessPartner;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface BusinessPartnerMapper {
  BusinessPartner toEntity(CreateBusinessPartnerDto dto);
  
  void updateEntity(EditBusinessPartnerDto dto, @MappingTarget BusinessPartner entity);

  BusinessPartnerDto toDto(BusinessPartner entity);

  BusinessPartnerListDto toListDto(BusinessPartner entity);
}
