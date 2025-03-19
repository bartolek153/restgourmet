package app.restgourmet.api.inventoryhandling.models;

import java.time.LocalDateTime;

import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.usermanagement.models.UserEntity;

public class InventoryAudit {
  private LocalDateTime auditDate;

  // status

  private UserEntity auditor;

  private String notes;
}
