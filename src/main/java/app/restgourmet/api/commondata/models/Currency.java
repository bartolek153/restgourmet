package app.restgourmet.api.commondata.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "currencies")
public class Currency extends BaseEntity {
  @Column(unique = true)
  private String code;
  private String description;
}
