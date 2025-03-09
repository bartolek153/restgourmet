package app.restgourmet.api.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.ListUserFiltersDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.service.spec.IUserService;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.CustomPageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/users")
@PreAuthorize(AppConstants.Security.Authorizations.ADMIN)
@Tag(name = "Users", description = "User endpoints")
public class UserController {
  private final IUserService userService;
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @Operation(summary = "List all users")
  @PreAuthorize(AppConstants.Security.Authorizations.USER_READ)
  public ResponseEntity<?> listUsers(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "name") final String sort,
      final ListUserFiltersDto filters) {
        log.info("Filters: {}", filters);
        return ResponseEntity.ok(userService.listUsers(
      CustomPageRequest.of(page, size, order, sort), filters)
    );
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a user by id")
  @PreAuthorize(AppConstants.Security.Authorizations.USER_READ)
  public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @PostMapping
  @Operation(summary = "Create a new user")
  @PreAuthorize(AppConstants.Security.Authorizations.USER_WRITE)
  public ResponseEntity<CreateUserDto> createUser(@Valid @RequestBody CreateUserDto user) {
    return ResponseEntity.ok(userService.createUser(user));
  }
  
  @PutMapping("/{id}")
  @Operation(summary = "Edit an existing user")
  @PreAuthorize(AppConstants.Security.Authorizations.USER_WRITE)
  public ResponseEntity<?> editUser(@RequestBody @Valid EditUserDto user, @PathVariable UUID id) {
    return ResponseEntity.ok(userService.editUser(id, user));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a user by id")
  @PreAuthorize(AppConstants.Security.Authorizations.USER_WRITE)
  public ResponseEntity<Void> deleteUser(@PathVariable @NotNull UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/disable")
  @Operation(summary = "Disable an user account")
  public ResponseEntity<Void> disableUser(UUID id) {
    userService.disableUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/enable")
  @Operation(summary = "Enable an user account")
  public ResponseEntity<Void> enableUser(UUID id) {
    userService.enableUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/reset-password")
  @Operation(summary = "Reset the password of an user account")
  public ResponseEntity<Void> resetPassword(UUID id) {
    // TODO: Implement password reset functionality
    // userService.resetPassword(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/profile")
  @Operation(summary = "Get the current authenticated user details")
  @PreAuthorize(AppConstants.Security.Authorizations.AUTHENTICATED)
  public ResponseEntity<UserEntity> getProfile() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok(userService.getProfile(auth));
  }

  @PutMapping("/profile")
  @Operation(summary = "Edit the current authenticated user details")
  @PreAuthorize(AppConstants.Security.Authorizations.AUTHENTICATED)
  public ResponseEntity<Void> editProfile(@RequestBody EditProfileDto data) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    userService.editProfile(auth, data);
    return ResponseEntity.noContent().build();
  }
}
