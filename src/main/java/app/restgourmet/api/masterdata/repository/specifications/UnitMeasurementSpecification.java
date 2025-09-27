package app.restgourmet.api.masterdata.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListFiltersDto;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class UnitMeasurementSpecification {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String BASE_UNIT = "baseUnit";

  public static Specification<UnitMeasurement> filterBy(UnitMeasurementListFiltersDto filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(filters.getQ())) {
        String q = String.format("%%s%", filters.getQ().toLowerCase());
        predicates.add(
            cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"));
      }

      if (filters.getBaseUnitId() != null) {
        predicates.add(
            cb.equal(root.get(BASE_UNIT).get(ID), filters.getBaseUnitId()));
      }

      if (filters.getIds() != null) {
        predicates.add(
            root.get(ID).in(filters.getIds()));
      }

      if (filters.getReferenceUnitId() != null) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<UnitMeasurement> refRoot = subquery.from(UnitMeasurement.class);
        subquery.select(refRoot.get(BASE_UNIT))
            .where(cb.equal(refRoot.get(ID), filters.getReferenceUnitId()));

        predicates.add(cb.equal(root.get(BASE_UNIT), subquery));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
