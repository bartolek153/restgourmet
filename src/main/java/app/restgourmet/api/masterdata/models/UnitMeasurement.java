package app.restgourmet.api.masterdata.models;

import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.models.BaseUnit;
import app.restgourmet.api.shared.models.BaseEntity;
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
@Table(name = "units_measurement")
public class UnitMeasurement extends BaseEntity {
  @NotNull
  @Column(nullable = false)
  private String description;

  @Column
  private String shortDescription;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "base_unit_id", nullable = false)
  private BaseUnit baseUnit;

  @Column
  private Double conversionFactor;

  public String toString() {
    return StringUtils.hasText(shortDescription) ? shortDescription + " - " : "" + description;
  }
}
