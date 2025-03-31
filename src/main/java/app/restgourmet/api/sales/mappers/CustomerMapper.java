package app.restgourmet.api.sales.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.sales.dto.CustomerDto;
import app.restgourmet.api.sales.dto.CustomerListDto;
import app.restgourmet.api.sales.models.Customer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    void updateEntity(CustomerDto dto, @MappingTarget Customer entity);

    CustomerListDto toListDto(Customer entity);

    CustomerDto toDto(Customer entity);
}
