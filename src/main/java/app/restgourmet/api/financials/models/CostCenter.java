package app.restgourmet.api.financials.models;

import app.restgourmet.api.shared.models.BaseEntity;
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
@Table(name = "cost_centers")
public class CostCenter extends BaseEntity {
  @NotNull
  private String name;

  @NotNull
  private String code;

  private boolean active;
}
