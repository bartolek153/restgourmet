package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;
import java.util.UUID;

import app.restgourmet.api.commondata.dto.storage.ImageDto;
import app.restgourmet.api.usermanagement.enums.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserDto {
  @NotNull
  private String name;
  @NotNull
  private UserType type;
  @NotNull
  private String email;
  @NotEmpty
  private String nickname;

  private List<ImageDto> picture;
  private List<UUID> roleIds;
  private List<UUID> groupIds;

  public String getNickname() {
    return nickname.toLowerCase();
  }

  public String getEmail() {
    return email.toLowerCase();
  }
}
