package app.restgourmet.api.usermanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class AuditableEntity extends BaseEntity {
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
