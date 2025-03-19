package app.restgourmet.api.usermanagement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;

public interface IUserGroupService {
  PagedModel<UserGroupListDto> list(PageRequest pagReq, UserGroupListFiltersDto dto);

  UserGroupDto getOne(UUID id);

  UUID create(UserGroupDto dto);

  void edit(UUID id, UserGroupDto dto);

  void delete(UUID id);
}
