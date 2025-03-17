package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_families")
public class ProductFamily extends BaseEntity {
  @NotNull
  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private ProductCategory category;
}
