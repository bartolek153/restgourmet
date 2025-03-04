package app.restgourmet.api.usermanagement.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {
  private UUID id;
  private String name;
  private String email;
  private String nickname;
  private String role;
  private boolean enabled;
  private LocalDateTime createdAt;
}
