package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;

import app.restgourmet.api.commondata.dto.ImageDto;
import lombok.Data;

@Data
public class UserDto {
  private String name;
  private String role;
  private String email;
  private String nickname;
  private List<ImageDto> picture;
  private List<String> permissions;
}
