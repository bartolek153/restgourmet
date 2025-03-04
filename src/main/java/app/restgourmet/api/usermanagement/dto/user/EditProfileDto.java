package app.restgourmet.api.usermanagement.dto.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EditProfileDto {
  private String name;
  private String email;
  private String password;
  private MultipartFile profileImage;
}
