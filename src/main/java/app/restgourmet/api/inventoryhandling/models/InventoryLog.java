package app.restgourmet.api.inventoryhandling.models;

import java.time.LocalDateTime;
import java.util.UUID;

import app.restgourmet.api.inventoryhandling.enums.TransactionType;
import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.shared.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transactions")
public class InventoryLog extends BaseEntity {

  private Warehouse warehouse;

  private Product product;

  private TransactionType type;

  private Double quantity;

  private LocalDateTime transactionDate;

  private String source;

  private String destination;

  private UUID reference;
}
