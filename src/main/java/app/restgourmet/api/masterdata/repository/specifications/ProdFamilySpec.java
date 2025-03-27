package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.family.ProdFamilyListFiltersDto;
import app.restgourmet.api.masterdata.models.ProductFamily;

public class ProdFamilySpec {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String CATEGORY = "category";

  public static Specification<ProductFamily> filterBy(ProdFamilyListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(categorySearch(filters.getCategoryId()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<ProductFamily> qSearch(String q) {
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

  private static Specification<ProductFamily> categorySearch(UUID categoryId) {
    return (root, query, cb) -> {
      if (categoryId == null)
        return cb.conjunction();

      return cb.equal(root.get(CATEGORY).get(ID), categoryId);
    };
  }

  private static Specification<ProductFamily> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
