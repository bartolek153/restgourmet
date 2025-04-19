package app.restgourmet.api.procurement.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListFiltersDto;
import app.restgourmet.api.procurement.models.ExpenseCategory;

public class ExpenseCategorySpec {
  private static final String ID = "id";
  private static final String NAME = "name";

  public static Specification<ExpenseCategory> filterBy(ExpenseCategoryListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<ExpenseCategory> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<ExpenseCategory> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
