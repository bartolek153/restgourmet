package app.restgourmet.api.sales.dto.customer;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CustomerListFiltersDto {
  private String q;
  private List<UUID> ids;
}
