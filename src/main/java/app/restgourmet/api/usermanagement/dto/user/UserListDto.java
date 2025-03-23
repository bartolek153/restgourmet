package app.restgourmet.api.usermanagement.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

import app.restgourmet.api.usermanagement.enums.UserType;
import lombok.Data;

@Data
public class UserListDto {
  private UUID id;
  private String name;
  private String email;
  private String nickname;
  private UserType type;
  private boolean enabled;
  private LocalDateTime createdAt;
}
