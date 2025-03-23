package app.restgourmet.api.usermanagement.dto.user;

import lombok.Data;

@Data
public class UserListFiltersDto {
  public String enabled = "";
  public String q = "";
}
