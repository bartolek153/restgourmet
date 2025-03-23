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

import app.restgourmet.api.usermanagement.dto.group.CreateUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.EditUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.service.spec.UserGroupService;
import app.restgourmet.api.usermanagement.service.spec.UserService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/groups")
@Tag(name = "User Group", description = "User group endpoints")
public class UserGroupController {
  private final UserGroupService userGroupService;
  private final UserService userService;

  public UserGroupController(
      UserGroupService userGroupService,
      UserService userService) {
    this.userGroupService = userGroupService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<UserGroupListDto>> listGroups(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      @ParameterObject final UserGroupListFiltersDto filters) {
    return ResponseEntity.ok(
        userGroupService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserGroupDto> getGroup(@PathVariable UUID id) {
    return ResponseEntity.ok(userGroupService.getOne(id));
  }

  @GetMapping("/permissions")
  public ResponseEntity<List<PermissionDto>> getUserGroupPermissions(@RequestParam UUID id) {
    return ResponseEntity.ok(userGroupService.getPermissions(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createGroup(@RequestBody @Valid CreateUserGroupDto dto) {
    UserEntity user = userService.getAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
    UUID id = userGroupService.create(dto, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateGroup(@PathVariable UUID id, @RequestBody @Valid EditUserGroupDto dto) {
    UserEntity user = userService.getAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
    userGroupService.edit(id, dto, user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
    userGroupService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
