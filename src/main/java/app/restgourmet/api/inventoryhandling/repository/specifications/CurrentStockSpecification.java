package app.restgourmet.api.inventoryhandling.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.inventoryhandling.dto.stock.InventoryListFiltersDto;
import app.restgourmet.api.inventoryhandling.models.CurrentStock;
import jakarta.persistence.criteria.Predicate;

public class CurrentStockSpecification {
  private static final String NAME = "name";
  private static final String WAREHOUSE = "warehouse";
  private static final String DESCRIPTION = "description";
  private static final String PRODUCT = "product";
  private static final String QUANTITY = "quantity";

  public static Specification<CurrentStock> filterBy(InventoryListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filters.isWithStock()) {
        predicates.add(cb.gt(root.get(QUANTITY), 0));
      }

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());
        predicates.add(
            cb.or(
                cb.like(cb.lower(root.get(PRODUCT).get(DESCRIPTION)), q),
                cb.like(cb.lower(root.get(WAREHOUSE).get(NAME)), q)));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
