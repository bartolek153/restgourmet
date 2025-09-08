package app.restgourmet.api.inventoryhandling.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.shared.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name = "stock_taking_items")
public class StockTakingItem extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "stock_taking_id")
  private StockTaking stockTaking;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Min(0)
  private Double countedQuantity;

  @Min(0)
  private Double systemQuantity;

  @Min(0)
  private Double difference;
}
