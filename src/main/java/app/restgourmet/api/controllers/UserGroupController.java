package app.restgourmet.api.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListFiltersDto;
import app.restgourmet.api.usermanagement.service.spec.IUserGroupService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/groups")
@Tag(name = "User Group", description = "User group endpoints")
public class UserGroupController {
  private final IUserGroupService roleService;

  public UserGroupController(IUserGroupService roleService) {
    this.roleService = roleService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<UserGroupListDto>> listGroups(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      @ParameterObject final UserGroupListFiltersDto filters) {
    return ResponseEntity.ok(
        roleService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserGroupDto> getGroup(@PathVariable UUID id) {
    return ResponseEntity.ok(roleService.getOne(id));
  }

  @PostMapping
  public ResponseEntity<UUID> createGroup(@RequestBody @Valid UserGroupDto dto) {
    UUID id = roleService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateGroup(@PathVariable UUID id, @RequestBody @Valid UserGroupDto dto) {
    roleService.edit(id, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
    roleService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
