package app.restgourmet.api.inventoryhandling.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
public class ProductInventory extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "warehouse_id", nullable = false)
  private Warehouse warehouse;

  @Column
  private Double quantity;

  @Column
  private Double minQuantity;

  @ManyToOne
  @JoinColumn(name = "min_quantity_unit_id")
  private UnitMeasurement minQuantityUnit;

  @Column
  private Double maxQuantity;

  @ManyToOne
  @JoinColumn(name = "max_quantity_unit_id")
  private UnitMeasurement maxQuantityUnit;
}
