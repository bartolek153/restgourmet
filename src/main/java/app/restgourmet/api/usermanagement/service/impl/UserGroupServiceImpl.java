package app.restgourmet.api.usermanagement.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.dto.group.CreateUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.EditUserGroupDto;
import app.restgourmet.api.usermanagement.mappers.UserGroupMapper;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserGroup;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserGroupRepository;
import app.restgourmet.api.usermanagement.repository.specifications.UserGroupSpec;
import app.restgourmet.api.usermanagement.service.spec.UserGroupService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class UserGroupServiceImpl implements UserGroupService {

  @Autowired
  private UserGroupMapper userGroupMapper;

  private final UserGroupRepository userGroupRepository;
  private final PermissionRepository permissionRepository;

  public UserGroupServiceImpl(UserGroupRepository userGroupRepository, PermissionRepository permissionRepository) {
    this.userGroupRepository = userGroupRepository;
    this.permissionRepository = permissionRepository;
  }

  public PagedModel<UserGroupListDto> list(PageRequest pagReq, UserGroupListFiltersDto dto) {
    Specification<UserGroup> spec = UserGroupSpec.filterBy(dto);
    Page<UserGroup> groups = userGroupRepository.findAll(spec, pagReq);
    return new PagedModel<>(groups.map(userGroupMapper::toListDto));
  }

  public UserGroupDto getOne(UUID id) {
    return userGroupMapper.toDto(getById(id));
  }

  public List<PermissionDto> getPermissions(UUID id) {
    return getById(id).getPermissions().stream().map(Permission::toPermissionDto).toList();
  }

  public UUID create(CreateUserGroupDto dto, UserEntity user) {
    UserGroup userGroup = userGroupMapper.createToEntity(dto);

    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    userGroup.setPermissions(permissions);

    userGroup.setCreatedBy(user);
    userGroup.setUpdatedBy(user);

    return userGroupRepository.save(userGroup).getId();
  }

  public void edit(UUID id, EditUserGroupDto dto, UserEntity user) {
    UserGroup userGroup = getById(id);
    userGroupMapper.updateEntity(dto, userGroup);

    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    userGroup.setPermissions(permissions);

    userGroup.setCreatedBy(user);
    userGroup.setUpdatedBy(user);

    userGroupRepository.save(userGroup);
  }

  @Transactional
  public void delete(UUID id) {
    UserGroup group = getById(id);

    if (userGroupRepository.existsByUsersNotEmpty()) {
      throw new BadRequestException(AppConstants.ErrorMessages.ROLE_DELETE_DEPS);
    }

    group.getPermissions().clear();
    userGroupRepository.save(group);

    userGroupRepository.delete(group);
  }

  private UserGroup getById(UUID id) {
    return userGroupRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(AppConstants.ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND));
  }

  private Set<Permission> extractPermissionObjs(List<String> strPermissions) {
    Set<Permission> permissions = new HashSet<>();
    ;

    for (String permStr : strPermissions) {
      permissions.add(permissionRepository.findByName(permStr)
          .orElseThrow(() -> new ResourceNotFoundException(
              permStr + ": " + AppConstants.ErrorMessages.PERMISSION_NOT_FOUND)));
    }

    return permissions;
  }
}
