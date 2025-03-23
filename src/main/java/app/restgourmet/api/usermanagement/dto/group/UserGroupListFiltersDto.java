package app.restgourmet.api.usermanagement.dto.group;

import java.util.UUID;

import lombok.Data;

@Data
public class UserGroupListFiltersDto {
  private String permissionName;
  private UUID userId;
  private UUID roleId;
}
