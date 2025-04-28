package app.restgourmet.api.financials.models;

import java.time.LocalDateTime;
import java.util.UUID;

import app.restgourmet.api.financials.enums.PayableStatus;
import app.restgourmet.api.shared.models.BaseEntity;

public class Payable extends BaseEntity {
  private UUID vendor;

  private LocalDateTime dueDate;

  private Double totalAmount;

  private Double paidAmount;

  private PayableStatus status;

  private String description;
}
