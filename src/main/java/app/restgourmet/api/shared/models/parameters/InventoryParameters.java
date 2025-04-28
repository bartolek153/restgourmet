package app.restgourmet.api.shared.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
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
@Table(name = "inventory_parameters", indexes = {
    @Index(name = "idx_created_at", columnList = "createdAt")
})
public class InventoryParameters extends BaseParameter {
  @NotNull
  private boolean stockTracingMandatory;
}
