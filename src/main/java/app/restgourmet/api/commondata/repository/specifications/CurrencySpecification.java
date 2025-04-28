package app.restgourmet.api.commondata.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.dto.currency.CurrencyListFiltersDto;
import app.restgourmet.api.commondata.models.Currency;
import jakarta.persistence.criteria.Predicate;

public class CurrencySpecification {
  private static final String DESCRIPTION = "description";
  private static final String CODE = "code";

  public static Specification<Currency> filterBy(CurrencyListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());

        predicates.add(
            cb.or(
                cb.like(root.get(CODE), q),
                cb.like(root.get(DESCRIPTION), q)));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
