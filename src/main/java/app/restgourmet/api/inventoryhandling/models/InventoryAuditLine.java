package app.restgourmet.api.inventoryhandling.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;

public class InventoryAuditLine {
  private InventoryAudit inventoryAudit;

  private Product product;

  private Double expectedQuantity;

  private Double realQuantity;

  private UnitMeasurement unit;

  private Double Variance;

  private String notes;
}
