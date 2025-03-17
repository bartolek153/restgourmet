package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.masterdata.enums.ProductOrigin;
import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AuditableEntity {
  @Column(nullable = true, unique = true)
  private String sku;

  @NotNull
  @Column
  private String description;

  @ManyToOne
  @JoinColumn(name = "group_id")
  private ProductGroup group;

  @Column
  @NotNull
  private ProductOrigin origin;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "inventory_unit_id", nullable = false)
  private UnitMeasurement inventoryUnit;
  
  @ManyToOne
  @JoinColumn(name = "purchase_unit_id", nullable = false)
  private UnitMeasurement purchaseUnit;

  @Column
  private Double price;
}
