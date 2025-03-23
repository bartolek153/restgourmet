package app.restgourmet.api.usermanagement.repository.specifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.usermanagement.dto.role.RoleListFiltersDto;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserEntity;
import jakarta.persistence.criteria.Join;

public class RoleSpec {
  private static final String USERS = "users";
  private static final String NAME = "name";
  private static final String PERMISSIONS = "permissions";
  private static final String ID = "id";

  public static Specification<Role> filterBy(RoleListFiltersDto filters) {
    return Specification.where(hasPermissionName(filters.getPermissionName()))
        .and(hasUserId(filters.getUserId()))
        .and(hasRoleId(filters.getRoleId()));
  }

  private static Specification<Role> hasPermissionName(String name) {
    return (root, query, cb) -> {
      if (StringUtils.hasText(name)) {
        Join<Role, Permission> rolePermissions = root.join(PERMISSIONS);
        return cb.equal(rolePermissions.get(NAME), name);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Role> hasUserId(UUID userId) {
    return (root, query, cb) -> {
      if (userId != null) {
        Join<Role, UserEntity> roleUsers = root.join(USERS);
        return cb.equal(roleUsers.get(ID), userId);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Role> hasRoleId(UUID roleId) {
    return (root, query, cb) -> {
      if (roleId != null) {
        return cb.equal(root.get(ID), roleId);
      }

      return cb.conjunction();
    };
  }
}
