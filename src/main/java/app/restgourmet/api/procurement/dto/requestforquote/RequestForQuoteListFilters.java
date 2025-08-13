package app.restgourmet.api.procurement.dto.requestforquote;

import lombok.Data;

@Data
public class RequestForQuoteListFilters {
  private String q;
  private String requisitionNumber;
}
