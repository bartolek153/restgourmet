package app.restgourmet.api.usermanagement.dto.role;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class RoleDto {
  private String name;
  private List<String> permissions;
  private String createdBy;
  private LocalDateTime createdAt;
  private String updatedBy;
  private LocalDateTime updatedAt;
}