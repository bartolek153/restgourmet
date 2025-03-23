package app.restgourmet.api.masterdata.repository.specifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.address.AddressListFiltersDto;
import app.restgourmet.api.masterdata.models.Address;


public class AddressSpec {
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";

  public static Specification<Address> filterBy(AddressListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()));
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
          cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"));
    };
  }
}
