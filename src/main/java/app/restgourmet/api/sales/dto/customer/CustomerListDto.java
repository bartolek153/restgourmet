package app.restgourmet.api.sales.dto.customer;

import java.util.UUID;

import lombok.Data;

@Data
public class CustomerListDto {
  private UUID id;
  private String name;
}
