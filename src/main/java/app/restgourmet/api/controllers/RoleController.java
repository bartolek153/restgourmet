package app.restgourmet.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListFiltersDto;
import app.restgourmet.api.usermanagement.dto.role.RolePermissionDto;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.service.spec.IRoleService;
import app.restgourmet.api.usermanagement.service.spec.IUserService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/roles")
@Tag(name = "Role", description = "User roles endpoints")
public class RoleController {

  private final IRoleService roleService;
  private final IUserService userService;

  public RoleController(
      IRoleService roleService,
      IUserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<RoleListDto>> listRole(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      @ParameterObject final RoleListFiltersDto filters) {
    return ResponseEntity.ok(
        roleService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDto> getRole(@PathVariable UUID id) {
    return ResponseEntity.ok(roleService.getOne(id));
  }

  @GetMapping("/permissions")
  public ResponseEntity<List<RolePermissionDto>> getRolePermissions(@RequestParam UUID id) {
    return ResponseEntity.ok(roleService.getPermissions(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createRole(@RequestBody @Valid RoleDto dto) {
    UserEntity user = userService.getAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
    UUID id = roleService.create(dto, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRole(@PathVariable UUID id, @RequestBody @Valid RoleDto dto) {
    UserEntity user = userService.getAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
    roleService.edit(id, dto, user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable UUID id) {
    roleService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
