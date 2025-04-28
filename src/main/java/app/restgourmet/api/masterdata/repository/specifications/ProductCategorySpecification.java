package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.category.ProdCategoryListFiltersDto;
import app.restgourmet.api.masterdata.models.ProductCategory;

public class ProductCategorySpecification {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";

  public static Specification<ProductCategory> filterBy(ProdCategoryListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<ProductCategory> qSearch(String q) {
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

  private static Specification<ProductCategory> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
