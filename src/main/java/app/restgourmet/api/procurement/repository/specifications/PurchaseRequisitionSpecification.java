package app.restgourmet.api.procurement.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.procurement.dto.purchaserequisition.PurchaseRequisitionListFilters;
import app.restgourmet.api.procurement.models.PurchaseRequisition;
import jakarta.persistence.criteria.Predicate;

public class PurchaseRequisitionSpecification {
  private static final String STATUS = "status";
  private static final String REQUISITION_NUMBER = "requisitionNumber";

  public static Specification<PurchaseRequisition> filterBy(PurchaseRequisitionListFilters filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());
        predicates.add(
            cb.or(cb.like(cb.lower(root.get(REQUISITION_NUMBER)), q)));
      }

      if (filters.getStatus() != null) {
        cb.equal(root.get(STATUS), filters.getStatus());
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
