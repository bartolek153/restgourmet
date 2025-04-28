package app.restgourmet.api.sales.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.sales.dto.ordercategory.SalesOrderCategoryListFiltersDto;
import app.restgourmet.api.sales.models.SalesOrderCategory;
import jakarta.persistence.criteria.Predicate;

public class SalesOrderCategorySpecification {

  private static final String DESCRIPTION = "description";
  private static final String CODE = "code";
  private static final String ID = "id";

  public static Specification<SalesOrderCategory> filterBy(SalesOrderCategoryListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());

        predicates.add(
            cb.or(
                cb.equal(cb.lower(root.get(CODE)), q),
                cb.equal(cb.lower(root.get(DESCRIPTION)), q)));
      }

      if (filters.getIds() != null) {
        predicates.add(
            root.get(ID).in(filters.getIds()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
