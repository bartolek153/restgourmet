package app.restgourmet.api.usermanagement.dto.group;

import java.util.UUID;

import lombok.Data;

@Data
public class UserGroupListDto {
  private UUID id;
  private String name;
}
