package app.restgourmet.api.usermanagement.service.spec;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.dto.role.CreateRoleDto;
import app.restgourmet.api.usermanagement.dto.role.EditRoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListFiltersDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

public interface IRoleService {
  PagedModel<RoleListDto> list(PageRequest pagReq, RoleListFiltersDto dto);

  RoleDto getOne(UUID id);

  List<PermissionDto> getPermissions(UUID id);

  UUID create(CreateRoleDto dto, UserEntity auditUser);

  void edit(UUID id, EditRoleDto dto, UserEntity auditUser);

  void delete(UUID id);
}
