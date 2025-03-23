package app.restgourmet.api.usermanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UUID>, JpaSpecificationExecutor<UserGroup> {
  Optional<Role> findByName(String name);

  boolean existsByUsersNotEmpty();
}
