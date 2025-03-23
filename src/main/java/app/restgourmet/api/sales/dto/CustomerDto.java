package app.restgourmet.api.sales.dto;

import java.util.UUID;

import app.restgourmet.api.masterdata.dto.address.AddressDto;
import lombok.Data;

@Data
public class CustomerDto {
    private String name;
    private String email;
    private String phone;
    private UUID addressId;
    private AddressDto address;
}
