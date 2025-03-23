package app.restgourmet.api.usermanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

import app.restgourmet.api.usermanagement.dto.permission.PermissionDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;

@Getter
@Setter
@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  private Set<Role> roles;

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  private Set<UserGroup> groups;

  private PermissionCategory category;

  public PermissionDto toPermissionDto() {
    return new PermissionDto(this.name);
  }
}
