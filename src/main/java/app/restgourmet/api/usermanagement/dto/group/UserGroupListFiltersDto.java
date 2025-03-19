package app.restgourmet.api.usermanagement.dto.group;

import lombok.Data;

@Data
public class UserGroupListFiltersDto {
  private String q;
  private String permission;
}
