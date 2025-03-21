package app.restgourmet.api.usermanagement.service.spec;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListFiltersDto;
import app.restgourmet.api.usermanagement.dto.role.RolePermissionDto;

public interface IRoleService {
  PagedModel<RoleListDto> list(PageRequest pagReq, RoleListFiltersDto dto);

  RoleDto getOne(UUID id);

  List<RolePermissionDto> getPermissions(UUID id);

  UUID create(RoleDto dto);

  void edit(UUID id, RoleDto dto);

  void delete(UUID id);
}
