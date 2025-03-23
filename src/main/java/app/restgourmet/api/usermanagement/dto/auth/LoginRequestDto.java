package app.restgourmet.api.usermanagement.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDto {

  @Schema(example = "johndoe@test.com")
  private String identifier;

  @Schema(example = "password")
  private String password;

  public String getIdentifier() {
    return this.identifier.toLowerCase();
  }
}
