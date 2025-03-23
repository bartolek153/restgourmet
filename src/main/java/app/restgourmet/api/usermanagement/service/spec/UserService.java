package app.restgourmet.api.usermanagement.service.spec;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;

import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListFiltersDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

public interface UserService {
  PagedModel<UserListDto> listUsers(PageRequest pageRequest, UserListFiltersDto filters);

  UserDto getOne(UUID id);

  CreateUserDto create(CreateUserDto data);

  EditUserDto edit(UUID id, EditUserDto data);

  void delete(UUID id);

  void disable(UUID id);

  void enable(UUID id);

  UserEntity getAuthenticatedUser(Authentication auth);

  void editProfile(Authentication auth, EditProfileDto data);

  Optional<UserEntity> findEntity(UUID id);
}
