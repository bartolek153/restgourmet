package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserListFiltersDto {
  private String enabled = "";
  private String q = "";
  private List<UUID> ids;
}
