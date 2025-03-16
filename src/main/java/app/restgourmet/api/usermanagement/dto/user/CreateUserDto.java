package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;

import app.restgourmet.api.commondata.dto.ImageDto;
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
  @NotNull
  private List<String> permissions;

  @Schema(description = "The profile image of the user")
  private List<ImageDto> picture;

  public String getEmail() {
    return email.toLowerCase();
  }

  public String getNickname() {
    return nickname != null ? nickname.toLowerCase() : null;
  }
}
