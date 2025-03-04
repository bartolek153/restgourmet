package app.restgourmet.api.usermanagement.repository.projections;

import java.time.LocalDateTime;

public interface UserListProjection {
  String getId();
  String getName();
  String getEmail();
  String getNickname();
  String getRole();
  boolean isEnabled();
  LocalDateTime getCreatedAt();
}
