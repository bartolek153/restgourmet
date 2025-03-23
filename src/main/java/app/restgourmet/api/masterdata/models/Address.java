package app.restgourmet.api.masterdata.models;

import app.restgourmet.api.usermanagement.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String number;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP must be in format 00000-000 or 00000000")
  private String zipCode;  // TODO: validate depending on country

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String additionalInfo;
}
