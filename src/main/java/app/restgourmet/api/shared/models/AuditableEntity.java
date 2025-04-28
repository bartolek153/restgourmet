package app.restgourmet.api.shared.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class AuditableEntity extends BaseEntity {
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  // @CreatedBy
  // @Column(updatable = false)
  // private UserEntity createdBy;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  // @LastModifiedBy
  // private UserEntity updatedBy;
}
