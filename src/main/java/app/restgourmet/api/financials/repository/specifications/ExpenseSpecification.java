package app.restgourmet.api.financials.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.financials.dto.expense.ExpenseListFiltersDto;
import app.restgourmet.api.financials.models.Expense;

public class ExpenseSpecification {
  private static final String ID = "id";
  private static final String NAME = "name";

  public static Specification<Expense> filterBy(ExpenseListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<Expense> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<Expense> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
