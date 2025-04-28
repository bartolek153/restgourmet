package app.restgourmet.api.sales.models;

import app.restgourmet.api.sales.enums.SalesOrderCategoryType;
import app.restgourmet.api.shared.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "sales_order_types")
public class SalesOrderCategory extends BaseEntity {
  @Column(unique = true)
  @NotNull
  private String code;

  @NotNull
  private String description;
  
  @NotNull
  private SalesOrderCategoryType type;

  private boolean isDefault;
}
