package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.unitmeasure.UnitMeasurementListFiltersDto;
import app.restgourmet.api.masterdata.models.UnitMeasurement;

public class UnitMeasSpec {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String BASE_UNIT = "baseUnit";

  public static Specification<UnitMeasurement> filterBy(UnitMeasurementListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(baseUnitSearch(filters.getBaseUnitId()))
        .and(hasIds(filters.getIds()));
  }

  public static Specification<UnitMeasurement> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      return cb.or(
          cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"));
    };
  }

  public static Specification<UnitMeasurement> baseUnitSearch(UUID baseUnit) {
    return (root, query, cb) -> {
      if (baseUnit == null)
        return cb.conjunction();

      return cb.equal(root.get(BASE_UNIT).get(ID), baseUnit);
    };
  }

  public static Specification<UnitMeasurement> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
