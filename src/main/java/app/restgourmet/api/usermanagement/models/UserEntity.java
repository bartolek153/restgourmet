package app.restgourmet.api.usermanagement.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.restgourmet.api.usermanagement.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends AuditableEntity {

  @NotEmpty
  @Length(max = 120)
  @Column(nullable = false, length = 120)
  private String name;

  @NotEmpty
  @Length(max = 30)
  @Column(nullable = false, length = 30, unique = true)
  private String nickname;

  @Email(message = "Email should be valid")
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @Column
  private boolean enabled;

  private UserRole role;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "users_permissions",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private List<Permission> permissions = new ArrayList<>();

  @Column
  private UUID pictureId;

  public void eraseCredentials() {
    this.password = null;
  }
}
