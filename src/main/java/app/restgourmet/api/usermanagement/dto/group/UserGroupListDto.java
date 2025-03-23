package app.restgourmet.api.usermanagement.dto.group;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserGroupListDto {
  private UUID id;
  private String name;
  private String createdBy;
  private LocalDateTime updatedAt;
}
