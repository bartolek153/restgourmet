package app.restgourmet.api.shared.models.parameters;

import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseParameter extends AuditableEntity {
  private boolean isActive;

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public boolean isActive() {
    return this.isActive;
  }
}
