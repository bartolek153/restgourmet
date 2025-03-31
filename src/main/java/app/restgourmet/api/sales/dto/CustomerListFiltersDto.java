package app.restgourmet.api.sales.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CustomerListFiltersDto {
  private String q;
  private List<UUID> ids;
}
