package app.restgourmet.api.usermanagement.dto.group;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserGroupDto {
  private String name;
  private List<String> permissions;
  private String createdBy;
  private LocalDateTime createdAt;
  private String updatedBy;
  private LocalDateTime updatedAt;
}
