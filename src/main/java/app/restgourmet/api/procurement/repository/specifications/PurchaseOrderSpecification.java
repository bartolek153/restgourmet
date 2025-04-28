package app.restgourmet.api.procurement.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListFiltersDto;
import app.restgourmet.api.procurement.models.PurchaseOrder;
import jakarta.persistence.criteria.Predicate;

public class PurchaseOrderSpecification {
  private static final String CURRENCY = "currency";
  private static final String VENDOR = "vendor";
  private static final String STATUS = "status";
  private static final String EXPECTED_DELIVERY_DATE = "expectedDeliveryDate";
  private static final String ORDER_NUMBER = "orderNumber";

  public static Specification<PurchaseOrder> filterBy(PurchaseOrderListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());

        predicates.add(cb.or(
            cb.like(root.get(ORDER_NUMBER), q)));
      }

      if (filters.getExpectedDeliveryDate() != null) {
        predicates.add(
            cb.equal(root.get(EXPECTED_DELIVERY_DATE), filters.getExpectedDeliveryDate()));
      }

      if (filters.getStatus() != null) {
        predicates.add(cb.equal(root.get(STATUS), filters.getStatus()));
      }

      if (filters.getVendorId() != null) {
        predicates.add(cb.equal(root.get(VENDOR), filters.getVendorId()));
      }

      if (filters.getCurrencyId() != null) {
        predicates.add(cb.equal(root.get(CURRENCY), filters.getCurrencyId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
