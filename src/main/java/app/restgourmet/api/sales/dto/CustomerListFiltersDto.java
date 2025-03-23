package app.restgourmet.api.sales.dto;

import lombok.Data;

@Data
public class CustomerListFiltersDto {
    private String name;
    private String email;
    private String phone;
    private String city;
    private String state;
}
