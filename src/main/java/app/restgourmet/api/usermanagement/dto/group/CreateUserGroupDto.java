package app.restgourmet.api.usermanagement.dto.group;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserGroupDto {
  @NotNull
  private String name;

  @NotNull
  private List<String> permissions;
}
