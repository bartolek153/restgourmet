package app.restgourmet.api.procurement.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.shared.models.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request_for_quote_items")
public class RequestForQuoteItem extends AuditableEntity {
  private RequestForQuote requestForQuote;

  private Product product;

  private Double unitPrice;

  private Double quantity;

  private Double totalPrice;
}
