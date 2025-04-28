package app.restgourmet.api.usermanagement.dto.user;

import java.util.List;
import java.util.UUID;

import app.restgourmet.api.commondata.dto.storage.ImageDto;
import app.restgourmet.api.usermanagement.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class CreateUserDto {
  @Schema(description = "The full name of the user", example = "John Doe")
  @NotNull
  private String name;

  @Schema(description = "The role assigned to the user", example = "ADMIN")
  @NotNull
  private UserType type;

  @Schema(description = "The email address of the user", example = "johndoe@example.com")
  @NotNull
  private String email;

  @Schema(description = "The nickname of the user", example = "johndoe123")
  private String nickname;

  @Schema(description = "The profile image of the user")
  private List<ImageDto> picture;

  @Schema(description = "IAM roles associated to the user")
  private List<UUID> roleIds;

  @Schema(description = "IAM groups associated to the user")
  private List<UUID> groupIds;

  // TODO: implement
  private boolean createEmployee;

  public String getEmail() {
    return email.toLowerCase();
  }

  public String getNickname() {
    return nickname != null ? nickname.toLowerCase() : null;
  }
}
