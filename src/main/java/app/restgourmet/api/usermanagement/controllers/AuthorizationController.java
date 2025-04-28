package app.restgourmet.api.usermanagement.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.usermanagement.dto.permission.PermissionListDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.service.spec.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/authorization")
@Tag(name = "Authorization", description = "Authorization endpoints")
public class AuthorizationController {
  private final PermissionService permissionService;

  public AuthorizationController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping("/permissions")
  @Operation(summary = "Get all permissions for a category")
  @PreAuthorize("hasAnyAuthority(@permissions.ADMIN)")
  public ResponseEntity<List<PermissionListDto>> getPermissions(
      @RequestParam(required = false) PermissionCategory category) {
    return ResponseEntity.ok(permissionService.getPermissions(category));
  }
}
