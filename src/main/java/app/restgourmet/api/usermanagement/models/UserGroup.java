package app.restgourmet.api.usermanagement.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "permission_groups")
public class UserGroup extends AuditableEntity {

  @NotNull
  private String name;

  @ManyToMany
  @JoinTable(
      name = "permission_group_permissions", 
      joinColumns = @JoinColumn(name = "permission_group_id"), 
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;
}
