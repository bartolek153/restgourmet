package app.restgourmet.api.usermanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parameters")
public class Parameter extends AuditableEntity {
  
  @Column(unique = true)
  private String optionKey;
  
  private String optionValue;
}
