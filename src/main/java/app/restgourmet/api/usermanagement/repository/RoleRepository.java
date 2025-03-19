package app.restgourmet.api.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import app.restgourmet.api.usermanagement.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {  
    Optional<Role> findByName(String name);
}
