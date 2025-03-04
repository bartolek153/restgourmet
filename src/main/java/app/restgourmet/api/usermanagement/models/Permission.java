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
@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

  @Column(unique = true)
  private String name;
}
