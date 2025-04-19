package app.restgourmet.api.sales.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.sales.dto.CustomerDto;
import app.restgourmet.api.sales.dto.CustomerListDto;
import app.restgourmet.api.sales.models.Customer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
  Customer toEntity(CustomerDto dto);
  
  void updateEntity(CustomerDto dto, @MappingTarget Customer entity);

  @Mapping(source = "businessPartner.name", target = "name")
  CustomerListDto toListDto(Customer entity);

  @Mapping(source = "billingAddress.id", target = "billingAddressId")
  @Mapping(source = "businessPartner.id", target = "partnerId")
  CustomerDto toDto(Customer entity);
}
