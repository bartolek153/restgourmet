package app.restgourmet.api.usermanagement.repository.specifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.usermanagement.dto.user.ListUserFiltersDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

public class UserSpec {
  private static final String ENABLED = "enabled";
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";
  private static final String NICKNAME = "nickname";

  public static Specification<UserEntity> filterBy(ListUserFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(isEnabled(filters.getEnabled()));
  }

  private static Specification<UserEntity> isEnabled(String enabled) {
    return (root, query, cb) -> {
      if (StringUtils.hasText(enabled) && ("true".equalsIgnoreCase(enabled) || "false".equalsIgnoreCase(enabled))) {
        return cb.equal(root.get(ENABLED), Boolean.parseBoolean(enabled));
      }
      
      return cb.conjunction();
    };
  }

  private static Specification<UserEntity> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
      }

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"),
          cb.like(root.get(EMAIL), "%" + q.toLowerCase() + "%"),
          cb.like(root.get(NICKNAME), "%" + q.toLowerCase() + "%"));
    };
  }
}
