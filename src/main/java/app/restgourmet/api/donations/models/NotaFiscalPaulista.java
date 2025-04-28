package app.restgourmet.api.donations.models;

import java.time.LocalDateTime;

import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notas_fiscais_paulista")
public class NotaFiscalPaulista extends AuditableEntity {
  private String cnpj;

  private String corporateName;

  private String number;

  private LocalDateTime issueDate;

  private Double totalAmount;

  private Double credits;

  private String status;
}
