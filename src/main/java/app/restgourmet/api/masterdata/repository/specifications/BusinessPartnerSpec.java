package app.restgourmet.api.masterdata.repository.specifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListFiltersDto;
import app.restgourmet.api.masterdata.models.BusinessPartner;

public class BusinessPartnerSpec {
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";
  private static final String PHONE = "phone";

  public static Specification<BusinessPartner> filterBy(BusinessPartnerListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()));
  }

  private static Specification<BusinessPartner> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
        // Not a UUID, continue with other filters
      }

      return cb.or(
          cb.like(cb.lower(root.get(NAME)), "%" + q.toLowerCase() + "%"),
          cb.like(cb.lower(root.get(EMAIL)), "%" + q.toLowerCase() + "%"),
          cb.like(cb.lower(root.get(PHONE)), "%" + q.toLowerCase() + "%"));
    };
  }
}
