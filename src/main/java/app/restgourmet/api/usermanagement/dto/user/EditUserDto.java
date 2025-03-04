package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserDto {
  @NotNull private String name;
  @NotNull private String role;
  private String email;
  private String nickname;
  private String pictureId;
  private List<String> permissions;

  public void normalizeNickname() {
    nickname = nickname.toLowerCase();
  }

  public void normalizeEmail() {
    email = email.toLowerCase();
  }
}
