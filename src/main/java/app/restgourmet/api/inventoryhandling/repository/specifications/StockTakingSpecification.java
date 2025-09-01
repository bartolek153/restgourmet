package app.restgourmet.api.inventoryhandling.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;
import app.restgourmet.api.inventoryhandling.models.StockTaking;
import jakarta.persistence.criteria.Predicate;

public class StockTakingSpecification {

  private static final String CREATED_BY = "createdBy";
  private static final String START_DATE = "startDate";
  private static final String END_DATE = "endDate";
  private static final String OBSERVATION = "observation";

  public static Specification<StockTaking> filterBy(StockTakingListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());
        predicates.add(
                cb.like(cb.lower(root.get(OBSERVATION)), q)
        );
      }

      if (filters.getStartDate() != null) {
        predicates.add(cb.equal(root.get(START_DATE), filters.getStartDate()));
      }

      if (filters.getEndDate() != null) {
        predicates.add(cb.equal(root.get(END_DATE), filters.getEndDate()));
      }

      if (filters.getCreatedById() != null) {
        predicates.add(
            cb.equal(root.get(CREATED_BY), filters.getCreatedById())
        );
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}

