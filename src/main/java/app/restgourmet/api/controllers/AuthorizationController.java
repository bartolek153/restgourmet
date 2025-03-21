package app.restgourmet.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.usermanagement.dto.permission.PermissionCategoryDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionListDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.service.spec.IPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/authorization")
@Tag(name = "Authorization", description = "Authorization endpoints")
public class AuthorizationController {
  private final IPermissionService permissionService;

  public AuthorizationController(IPermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping("/categories")
  @Operation(summary = "Get all permission categories")
  @PreAuthorize("hasAnyAuthority(@permissions.ADMIN)")
  public ResponseEntity<List<PermissionCategoryDto>> getPermissionCategories() {
    return ResponseEntity.ok(permissionService.getPermissionCategories());
  }

  @GetMapping("/permissions")
  @Operation(summary = "Get all permissions for a category")
  @PreAuthorize("hasAnyAuthority(@permissions.ADMIN)")
  public ResponseEntity<List<PermissionListDto>> getPermissions(
      @RequestParam(required = true) PermissionCategory category) {
    return ResponseEntity.ok(permissionService.getPermissions(category));
  }
}
