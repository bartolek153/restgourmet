package app.restgourmet.api.inventoryhandling.models;

import java.time.LocalDateTime;
import java.util.List;

import app.restgourmet.api.inventoryhandling.enums.StockTakingStatus;
import app.restgourmet.api.masterdata.models.Warehouse;
import app.restgourmet.api.shared.models.BaseEntity;
import app.restgourmet.api.usermanagement.models.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "stock_takings")
public class StockTaking extends BaseEntity {
  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private UserEntity createdBy;

  private StockTakingStatus status;

  @ManyToOne
  @JoinColumn(name = "warehouse_id", nullable = false)
  private Warehouse warehouse;

  private String observation;

  @OneToMany
  private List<StockTakingItem> items;

  public void addItem(StockTakingItem item) {
    this.items.add(item);
  }  
}
