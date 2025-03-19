package app.restgourmet.api.usermanagement.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;
import app.restgourmet.api.usermanagement.mappers.IUserGroupMapper;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserGroup;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserGroupRepository;
import app.restgourmet.api.usermanagement.service.spec.IUserGroupService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class UserGroupService implements IUserGroupService {

  @Autowired
  private IUserGroupMapper roleMapper;
  private final UserGroupRepository roleRepository;
  private final PermissionRepository permissionRepository;

  public UserGroupService(UserGroupRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  public PagedModel<UserGroupListDto> list(PageRequest pagReq, UserGroupListFiltersDto dto) {
    Page<UserGroup> roles = roleRepository.findAll(pagReq);
    return new PagedModel<>(roles.map(roleMapper::toListDto));
  }

  public UserGroupDto getOne(UUID id) {
    return roleMapper.toDto(getById(id));
  }

  public UUID create(UserGroupDto dto) {
    UserGroup role = roleMapper.toEntity(dto);
    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    role.setPermissions(permissions);

    return roleRepository.save(role).getId();
  }

  public void edit(UUID id, UserGroupDto dto) {
    UserGroup role = getById(id);
    roleMapper.updateEntity(dto, role);
    
    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    role.setPermissions(permissions);
    
    roleRepository.save(role);
  }

  public void delete(UUID id) {
    if (!roleRepository.existsById(id)) {
      throw new ResourceNotFoundException(AppConstants.ErrorMessages.ROLE_NOT_FOUND);
    }

    roleRepository.deleteById(id);
  }

  private UserGroup getById(UUID id) {
    return roleRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND));
  }

  private Set<Permission> extractPermissionObjs(List<String> strPermissions) {
    Set<Permission> permissions = new HashSet<>();;

    for (String permStr : strPermissions) {
      permissions.add(permissionRepository.findByName(permStr)
          .orElseThrow(() -> new ResourceNotFoundException(
              permStr + ": " + AppConstants.ErrorMessages.PERMISSION_NOT_FOUND)));
    }

    return permissions;
  }
}
