package app.restgourmet.api.usermanagement.dto.role;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoleDto {
  @NotNull
  private String name;

  @NotNull
  private List<String> permissions;
}
