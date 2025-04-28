package app.restgourmet.api.sales.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import app.restgourmet.api.sales.dto.order.SalesOrderListFiltersDto;
import app.restgourmet.api.sales.models.SalesOrder;
import jakarta.persistence.criteria.Predicate;

public class SalesOrderSpecification {

  private static final String WAREHOUSE = "warehouse";
  private static final String DELIVERY_DATE = "deliveryDate";
  private static final String ORDER_DATE = "orderDate";
  private static final String PAYMENT_METHOD = "paymentMethod";
  private static final String STATUS = "status";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String CUSTOMER = "customer";
  private static final String ORDER_NUMBER = "orderNumber";

  public static Specification<SalesOrder> filterBy(SalesOrderListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filters.getOrderNumber() != null && !filters.getOrderNumber().isEmpty()) {
        predicates.add(cb.like(
            cb.lower(root.get(ORDER_NUMBER)),
            "%" + filters.getOrderNumber().toLowerCase() + "%"));
      }

      if (filters.getCustomerId() != null) {
        predicates.add(cb.equal(root.get(CUSTOMER).get(ID), filters.getCustomerId()));
      }

      if (filters.getCustomerName() != null && !filters.getCustomerName().isEmpty()) {
        predicates.add(cb.like(
            cb.lower(root.get(CUSTOMER).get(NAME)),
            "%" + filters.getCustomerName().toLowerCase() + "%"));
      }

      if (filters.getStatus() != null) {
        predicates.add(cb.equal(root.get(STATUS), filters.getStatus()));
      }

      if (filters.getPaymentMethod() != null) {
        predicates.add(cb.equal(root.get(PAYMENT_METHOD), filters.getPaymentMethod()));
      }

      if (filters.getOrderDateFrom() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get(ORDER_DATE), filters.getOrderDateFrom()));
      }

      if (filters.getOrderDateTo() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get(ORDER_DATE), filters.getOrderDateTo()));
      }

      if (filters.getDeliveryDateFrom() != null) {
        predicates.add(
            cb.greaterThanOrEqualTo(root.get(DELIVERY_DATE), filters.getDeliveryDateFrom()));
      }

      if (filters.getDeliveryDateTo() != null) {
        predicates
            .add(cb.lessThanOrEqualTo(root.get(DELIVERY_DATE), filters.getDeliveryDateTo()));
      }

      if (filters.getWarehouseId() != null) {
        predicates.add(cb.equal(root.get(WAREHOUSE).get(ID), filters.getWarehouseId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };

  }
}
