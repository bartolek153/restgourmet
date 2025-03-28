package app.restgourmet.api.masterdata.enums;

public enum ProductStatus {
  ACTIVE,
  BLOCKED, // can't transact with this product
  DISCONTINUED, // no longer produced
  OBSOLETE // replaced by newer version
}
