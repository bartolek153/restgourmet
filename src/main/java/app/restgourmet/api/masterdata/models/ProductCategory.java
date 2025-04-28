package app.restgourmet.api.masterdata.models;

import org.hibernate.validator.constraints.Length;

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
@Table(name = "product_categories")
public class ProductCategory extends BaseEntity {

  @NotNull
  @Length(max = 100)
  @Column(nullable = false, length = 100)
  private String description;
}
