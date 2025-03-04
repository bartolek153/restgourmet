package app.restgourmet.api.usermanagement.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected UUID id;
}
