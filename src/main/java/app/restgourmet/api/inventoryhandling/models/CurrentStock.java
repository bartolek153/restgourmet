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
public class CurrentStock extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "warehouse_id", nullable = false)
  private Warehouse warehouse;

  @Min(0)
  private Double qty;

  @Min(0)
  private Double minQty;

  @ManyToOne
  @JoinColumn(name = "min_quantity_unit_id")
  private UnitMeasurement minQtyUnit;

  @Min(0)
  private Double maxQty;

  @ManyToOne
  @JoinColumn(name = "max_quantity_unit_id")
  private UnitMeasurement maxQtyUnit;

  public void increaseQuantity(Double val) {
    this.qty = this.qty + val;
  }

  public void decreaseQuantity(Double val) {
    this.qty = this.qty - val;
  }

  public boolean quantityExceeded() {
    return this.qty > this.maxQty;
  }

  public boolean insufficientStock() {
    return this.qty < this.minQty;
  }
}
