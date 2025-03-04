package app.restgourmet.api.usermanagement.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import app.restgourmet.api.usermanagement.enums.UserRole;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;
import lombok.Getter;

@Getter
public class UserDetailsImpl implements UserDetails {

  private UUID id;

  private String password;

  private final String username;

  private final Collection<? extends GrantedAuthority> authorities;

  private final boolean accountNonExpired;

  private final boolean accountNonLocked;

  private final boolean credentialsNonExpired;

  private final boolean enabled;

  public UserDetailsImpl(UUID id, String password, String username, Collection<? extends GrantedAuthority> authorities,
      boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
    this.id = id;
    this.password = password;
    this.username = username;
    this.authorities = authorities;
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
    this.enabled = enabled;
  }

  public UserDetailsImpl(UserEntity user) {
    this.id = user.getId();
    this.username = user.getNickname();
    this.password = user.getPassword();
    this.enabled = user.isEnabled();
    this.authorities = mapPermissionsToAuthorities(user.getRole(), user.getPermissions());
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
  }

  private Collection<GrantedAuthority> mapPermissionsToAuthorities(UserRole role, List<Permission> permissions) {
    Collection<GrantedAuthority> authorities = permissions.stream()
        .map(perm -> new SimpleGrantedAuthority(perm.getName()))
        .collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

    return authorities;
  }
}
