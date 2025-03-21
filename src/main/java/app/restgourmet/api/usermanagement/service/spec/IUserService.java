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
  PagedModel<UserListDto> listUsers(PageRequest pageRequest, ListUserFiltersDto filters);

  UserDto getUser(UUID id);

  CreateUserDto createUser(CreateUserDto data);
  
  EditUserDto editUser(UUID id, EditUserDto data);
  
  void deleteUser(UUID id);

  void disableUser(UUID id);

  void enableUser(UUID id);

  UserEntity getAuthenticatedUser(Authentication auth);

  void editProfile(Authentication auth, EditProfileDto data);
}
