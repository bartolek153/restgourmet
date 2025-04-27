package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.masterdata.enums.ProductOrigin;
import app.restgourmet.api.masterdata.enums.ProductStatus;
import app.restgourmet.api.masterdata.enums.ProductType;
import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
  @Column(unique = true)
  private String sku;

  @NotNull
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  private ProductGroup group;

  @NotNull
  private ProductOrigin origin;

  @NotNull 
  private ProductStatus status;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_unit_id", nullable = false)
  private UnitMeasurement inventoryUnit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_unit_id")
  private UnitMeasurement purchaseUnit;

  private Double price;

  private boolean deleted = false;

  private ProductType type;

  private String image;
}
