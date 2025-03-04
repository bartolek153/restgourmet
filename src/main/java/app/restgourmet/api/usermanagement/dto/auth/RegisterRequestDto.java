package app.restgourmet.api.usermanagement.dto.auth;

import org.hibernate.validator.constraints.Length;

import app.restgourmet.api.usermanagement.models.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDto {
  
  @NotBlank
  @Schema(example = "John Doe")
  private String name;
  
  @NotBlank
  @Schema(example = "johndoe@test.com")
  private String email;

  @Schema(example = "wise_hawk_3458")
  private String nickname;

  @NotEmpty
  @Length(min = 8, max = 120)
  @Schema(example = "password")
  private String password;

  public UserEntity toUserEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setName(name);
    userEntity.setEmail(email);
    userEntity.setNickname(nickname);
    userEntity.setPassword(password);
    userEntity.setEnabled(true);
    return userEntity;
  }
}
