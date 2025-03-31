package app.restgourmet.api.sales.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.sales.dto.CustomerListFiltersDto;
import app.restgourmet.api.sales.models.Customer;

public class CustomerSpec {
  private static final String ID = "id";
  private static final String NAME = "name";

  public static Specification<Customer> filterBy(CustomerListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<Customer> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
      }

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<Customer> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
