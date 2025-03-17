package app.restgourmet.api.masterdata.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.UnitMeasurementListFiltersDto;
import app.restgourmet.api.masterdata.models.UnitMeasurement;

public class UnitMeasSpec {
  private static final String DESCRIPTION = "description";

  public static Specification<UnitMeasurement> filterBy(UnitMeasurementListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()));
  }

  public static Specification<UnitMeasurement> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      return cb.or(
          cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"));
    };
  }
}
