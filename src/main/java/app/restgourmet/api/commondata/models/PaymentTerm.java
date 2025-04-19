package app.restgourmet.api.commondata.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
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
@Table(name = "payment_terms")
@Entity
public class PaymentTerm extends BaseEntity {
  @NotNull
  private String description;
  
  private Double days;
}
