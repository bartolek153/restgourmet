package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class CreateUserDto {

  @NotNull
  @Schema(description = "The full name of the user", example = "John Doe")
  private String name;

  @NotNull
  @Schema(description = "The email address of the user", example = "johndoe@example.com")
  private String email;

  @NotNull
  @Schema(description = "The role assigned to the user", example = "ADMIN")
  private String role;

  @Schema(description = "The nickname of the user", example = "johndoe123")
  private String nickname;

  // @Schema(description = "The password for the user account", example = "Password123!")
  // private String password;

  @Schema(description = "A list of permissions granted to the user", example = "[\"READ_PRIVILEGES\", \"WRITE_PRIVILEGES\"]")
  private List<String> permissions;

  @Schema(description = "The profile image of the user")
  private String pictureId;

  public void normalizeNickname() {
    nickname = nickname.toLowerCase();
  }

  public void normalizeEmail() {
    email = email.toLowerCase();
  }
}
