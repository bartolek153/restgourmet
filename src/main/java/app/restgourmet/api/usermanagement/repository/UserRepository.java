package app.restgourmet.api.usermanagement.repository;

import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.projections.UserListProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
  Optional<UserEntity> findByNicknameOrEmail(String username, String email);

  boolean existsByNickname(String nickname);

  boolean existsByEmail(String email);

  Page<UserListProjection> findAllBy(Pageable pageable); // projections without specification
}
