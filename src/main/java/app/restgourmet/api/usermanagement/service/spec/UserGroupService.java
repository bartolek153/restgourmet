package app.restgourmet.api.usermanagement.service.spec;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.usermanagement.dto.group.CreateUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.EditUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

public interface UserGroupService {
  PagedModel<UserGroupListDto> list(PageRequest pagReq, UserGroupListFiltersDto dto);

  UserGroupDto getOne(UUID id);

  List<PermissionDto> getPermissions(UUID id);

  UUID create(CreateUserGroupDto dto, UserEntity auditUser);

  void edit(UUID id, EditUserGroupDto dto, UserEntity auditUser);

  void delete(UUID id);
}
