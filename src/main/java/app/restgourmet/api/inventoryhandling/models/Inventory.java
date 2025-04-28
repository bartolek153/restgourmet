package app.restgourmet.api.inventoryhandling.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.shared.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_inventory", uniqueConstraints = @UniqueConstraint(columnNames = { "product_id",
    "warehouse_id" }))
public class Inventory extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "warehouse_id", nullable = false)
  private Warehouse warehouse;

  @Min(0)
  private Double quantity;

  @Min(0)
  private Double allocatedQuantity;

  @Min(0)
  private Double threshold;

  @ManyToOne
  @JoinColumn(name = "min_quantity_unit_id")
  private UnitMeasurement thresholdUnit;

  @Min(0)
  private Double maxQuantityAllowed;

  @ManyToOne
  @JoinColumn(name = "max_quantity_unit_id")
  private UnitMeasurement maxQuantityAllowedUnit;

  public void increaseQuantity(Double add) {
    this.quantity = this.quantity + add;
  }

  public void decreaseQuantity(Double sub) {
    this.quantity = this.quantity - sub;
  }

  public boolean insufficientStock() {
    return this.quantity < 0;
  }
  
  public boolean quantityExceeded() {
    return this.quantity > this.maxQuantityAllowed;
  }

  public boolean underThreshold() {
    return this.quantity < this.threshold;
  }

  public void allocate(Double qty) {
    this.allocatedQuantity = this.allocatedQuantity + qty;
  }

  public void deallocate(Double qty) {
    this.allocatedQuantity = this.allocatedQuantity - qty;
  }
}
