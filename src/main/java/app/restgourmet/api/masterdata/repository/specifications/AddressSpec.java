package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.address.AddressListFiltersDto;
import app.restgourmet.api.masterdata.models.Address;

public class AddressSpec {
  private static final String ID = "id";
  private static final String ZIPCODE = "zipCode";
  private static final String STREET = "street";
  private static final String CITY = "city";

  public static Specification<Address> filterBy(AddressListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<Address> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
      }

      return cb.or(
          cb.like(cb.lower(root.get(STREET)), "%" + q.toLowerCase() + "%"),
          cb.like(cb.lower(root.get(CITY)), "%" + q.toLowerCase() + "%"),
          cb.like(cb.lower(root.get(ZIPCODE)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<Address> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
