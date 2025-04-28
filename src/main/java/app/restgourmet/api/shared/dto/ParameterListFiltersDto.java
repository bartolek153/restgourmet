package app.restgourmet.api.shared.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ParameterListFiltersDto {
  private String q;
  private List<UUID> ids;
}
