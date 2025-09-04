package app.restgourmet.api.usermanagement.repository.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.usermanagement.dto.user.UserListFiltersDto;
import app.restgourmet.api.usermanagement.models.UserEntity;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
  private static final String ENABLED = "enabled";
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";
  private static final String NICKNAME = "nickname";

  public static Specification<UserEntity> filterBy(UserListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        try {
          return cb.equal(root.get(ID), UUID.fromString(filters.getQ()));
        } catch (IllegalArgumentException ex) {
          String q = String.format("%%s%", filters.getQ().toLowerCase());

          predicates.add(
              cb.or(
                  cb.like(cb.lower(root.get(NAME)), q),
                  cb.like(root.get(EMAIL), q),
                  cb.like(root.get(NICKNAME), q)));
        }
      }

      if (StringUtils.hasText(filters.getEnabled())
          && ("true".equalsIgnoreCase(filters.getEnabled()) || "false".equalsIgnoreCase(filters.getEnabled()))) {
        predicates.add(
            cb.equal(root.get(ENABLED), Boolean.parseBoolean(filters.getEnabled())));
      }

      if (filters.getIds() != null) {
        predicates.add(
            root.get(ID).in(filters.getIds()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
