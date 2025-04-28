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

import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.dto.role.CreateRoleDto;
import app.restgourmet.api.usermanagement.dto.role.EditRoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListFiltersDto;
import app.restgourmet.api.usermanagement.mappers.RoleMapper;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.RoleRepository;
import app.restgourmet.api.usermanagement.repository.specifications.RoleSpecification;
import app.restgourmet.api.usermanagement.service.spec.RoleService;
import app.restgourmet.api.utils.AppConstants;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleMapper roleMapper;

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  public PagedModel<RoleListDto> list(PageRequest pagReq, RoleListFiltersDto dto) {
    Specification<Role> spec = RoleSpecification.filterBy(dto);
    Page<Role> roles = roleRepository.findAll(spec, pagReq);
    return new PagedModel<>(roles.map(roleMapper::toListDto));
  }

  public RoleDto getOne(UUID id) {
    return roleMapper.toDto(getById(id));
  }

  public List<PermissionDto> getPermissions(UUID id) {
    return getById(id).getPermissions().stream().map(Permission::toPermissionDto).toList();
  }

  public UUID create(CreateRoleDto dto, UserEntity user) {
    Role role = roleMapper.createToEntity(dto);

    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    role.setPermissions(permissions);

    role.setCreatedBy(user);
    role.setUpdatedBy(user);

    return roleRepository.save(role).getId();
  }

  public void edit(UUID id, EditRoleDto dto, UserEntity user) {
    Role role = getById(id);
    roleMapper.updateEntity(dto, role);

    Set<Permission> permissions = extractPermissionObjs(dto.getPermissions());
    role.setPermissions(permissions);

    role.setCreatedBy(user);
    role.setUpdatedBy(user);

    roleRepository.save(role);
  }

  @Transactional
  public void delete(UUID id) {
    Role role = getById(id);

    if (roleRepository.existsByUsersNotEmpty()) {
      throw new BadRequestException(AppConstants.ErrorMessages.ROLE_DELETE_DEPS);
    }

    role.getPermissions().clear();
    roleRepository.save(role);
    roleRepository.delete(role);
  }

  private Role getById(UUID id) {
    return roleRepository.findById(id)
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
