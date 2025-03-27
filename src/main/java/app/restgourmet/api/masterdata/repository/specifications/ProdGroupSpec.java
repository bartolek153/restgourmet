package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.group.ProdGroupListFiltersDto;
import app.restgourmet.api.masterdata.models.ProductGroup;

public class ProdGroupSpec {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String FAMILY = "family";

  public static Specification<ProductGroup> filterBy(ProdGroupListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(familySearch(filters.getFamilyId()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<ProductGroup> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
      }

      return cb.or(
          cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<ProductGroup> familySearch(UUID familyId) {
    return (root, query, cb) -> {
      if (familyId == null)
        return cb.conjunction();

      return cb.equal(root.get(FAMILY).get(ID), familyId);
    };
  }

  private static Specification<ProductGroup> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
