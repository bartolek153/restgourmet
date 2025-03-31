package app.restgourmet.api.masterdata.repository.specifications;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import app.restgourmet.api.masterdata.dto.product.ProductListFiltersDto;
import app.restgourmet.api.masterdata.enums.ProductOrigin;
import app.restgourmet.api.masterdata.enums.ProductStatus;
import app.restgourmet.api.masterdata.models.Product;

public class ProdSpec {
  private static final String INVENTORY_UNIT = "inventoryUnit";
  private static final String ORIGIN = "origin";
  private static final String CATEGORY = "category";
  private static final String FAMILY = "family";
  private static final String GROUP = "group";
  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String SKU = "sku";
  private static final String STATUS = "status";
  private static final String DELETED = "deleted";

  public static Specification<Product> filterBy(ProductListFiltersDto filters) {
    return Specification.where(qSearch(filters.getQ()))
        .and(hasGroupId(filters.getGroupId()))
        .and(hasFamilyId(filters.getFamilyId()))
        .and(hasCategoryId(filters.getCategoryId()))
        .and(hasOrigin(filters.getOrigin()))
        .and(hasInventoryUnitId(filters.getInventoryUnitId()))
        .and(hasStatuses(filters.getStatuses()))
        .and(hasDeleted(filters.getDeleted()))
        .and(hasIds(filters.getIds()));
  }

  private static Specification<Product> qSearch(String q) {
    return (root, query, cb) -> {
      if (!StringUtils.hasText(q))
        return cb.conjunction();

      try {
        return cb.equal(root.get(ID), UUID.fromString(q));
      } catch (IllegalArgumentException ex) {
      }

      return cb.or(
          cb.like(cb.lower(root.get(DESCRIPTION)), "%" + q.toLowerCase() + "%"),
          cb.like(cb.lower(root.get(SKU)), "%" + q.toLowerCase() + "%"));
    };
  }

  private static Specification<Product> hasGroupId(UUID groupId) {
    return (root, query, cb) -> {
      if (groupId != null) {
        return cb.equal(root.get(GROUP).get(ID), groupId);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasFamilyId(UUID familyId) {
    return (root, query, cb) -> {
      if (familyId != null) {
        return cb.equal(root.get(GROUP).get(FAMILY).get(ID), familyId);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasCategoryId(UUID categoryId) {
    return (root, query, cb) -> {
      if (categoryId != null) {
        return cb.equal(root.get(GROUP).get(FAMILY).get(CATEGORY).get(ID), categoryId);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasOrigin(ProductOrigin origin) {
    return (root, query, cb) -> {
      if (origin != null) {
        return cb.equal(root.get(ORIGIN), origin);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasInventoryUnitId(UUID inventoryUnitId) {
    return (root, query, cb) -> {
      if (inventoryUnitId != null) {
        return cb.equal(root.get(INVENTORY_UNIT).get(ID), inventoryUnitId);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasStatuses(List<ProductStatus> status) {
    return (root, query, cb) -> {
      if (status != null) {
        return root.get(STATUS).in(status);
      }

      return cb.conjunction();
    };
  }

  private static Specification<Product> hasDeleted(boolean deleted) {
    return (root, query, cb) -> {
      return cb.equal(root.get(DELETED), deleted);
    };
  }

  private static Specification<Product> hasIds(List<UUID> ids) {
    return (root, query, cb) -> ids == null ? cb.conjunction() : root.get(ID).in(ids);
  }
}
