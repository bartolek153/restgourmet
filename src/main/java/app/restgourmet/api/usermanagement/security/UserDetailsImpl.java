package app.restgourmet.api.usermanagement.security;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import app.restgourmet.api.usermanagement.enums.UserType;
import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.models.UserGroup;
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
    this.password = user.getPassword();
    this.username = user.getNickname();
    this.enabled = user.isEnabled();
    this.authorities = mapPermissionsToAuthorities(user.getType(), user.getRoles(), user.getGroups());
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
  }

  private Set<SimpleGrantedAuthority> mapPermissionsToAuthorities(
      UserType type,
      Set<Role> roles,
      Set<UserGroup> groups) {

    Set<SimpleGrantedAuthority> authorities = roles.stream()
        .flatMap(rl -> rl.getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getName())))
        .collect(Collectors.toSet());

    authorities.addAll(groups.stream()
        .flatMap(gp -> gp.getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getName())))
        .collect(Collectors.toSet()));

    authorities.add(new SimpleGrantedAuthority("ROLE_" + type.name()));

    return authorities;
  }
}
