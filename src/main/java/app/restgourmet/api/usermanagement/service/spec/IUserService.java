package app.restgourmet.api.usermanagement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;

import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.ListUserFiltersDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

public interface IUserService {
  public PagedModel<UserListDto> listUsers(PageRequest pageRequest, ListUserFiltersDto filters);

  public UserDto getUser(UUID id);

  public CreateUserDto createUser(CreateUserDto data);
  
  public EditUserDto editUser(UUID id, EditUserDto data);
  
  public void deleteUser(UUID id);

  public void disableUser(UUID id);

  public void enableUser(UUID id);

  public UserEntity getProfile(Authentication auth);

  public void editProfile(Authentication auth, EditProfileDto data);
}
