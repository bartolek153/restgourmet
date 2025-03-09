package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;

import app.restgourmet.api.commondata.dto.ImageDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserDto {
  @NotNull private String name;
  @NotNull private String role;
  @NotNull private String email;
  @NotEmpty private String nickname;
  private List<ImageDto> picture;
  private List<String> permissions;

  public String getNickname() {
    return nickname.toLowerCase();
  }

  public String getEmail() {
    return email.toLowerCase();
  }
}
