package app.restgourmet.api.commondata.models;

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
@Table(name = "base_units")
public class BaseUnit extends BaseEntity {
  @NotNull
  @Column(nullable = false)
  private String description;

  @Column
  private String shortDescription;
}
