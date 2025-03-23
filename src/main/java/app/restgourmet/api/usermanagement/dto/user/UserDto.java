package app.restgourmet.api.usermanagement.dto.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import app.restgourmet.api.commondata.dto.ImageDto;
import app.restgourmet.api.usermanagement.enums.UserType;
import lombok.Data;

@Data
public class UserDto {
  private String name;
  private UserType type;
  private String email;
  private String nickname;
  private List<ImageDto> picture;
  private List<UUID> roles;
  private List<UUID> groups;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
