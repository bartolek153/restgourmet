package app.restgourmet.api.procurement.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.procurement.dto.vendor.VendorListFiltersDto;
import app.restgourmet.api.procurement.models.Vendor;

public class VendorSpecification {
  private static final String ID = "id";
  private static final String NAME = "name";

  public static Specification<Vendor> filterBy(VendorListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<Vendor> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<Vendor> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
