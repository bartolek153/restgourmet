package app.restgourmet.api.sales.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CustomerListDto {
  private UUID id;
  private String name;
}
