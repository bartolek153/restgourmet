package app.restgourmet.api.commondata.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListFiltersDto;
import app.restgourmet.api.commondata.models.PaymentTerm;

public class PaymentTermSpecification {
  private static final String DESCRIPTION = "description";

  public static Specification<PaymentTerm> hasFilters(PaymentTermListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()));
  }

  private static Specification<PaymentTerm> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q)) {
        return cb.conjunction();
      }

      return cb.equal(cb.lower(root.get(DESCRIPTION)), q.toLowerCase());
    };
  }
}
