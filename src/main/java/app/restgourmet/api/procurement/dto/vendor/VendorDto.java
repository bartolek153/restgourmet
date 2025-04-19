package app.restgourmet.api.procurement.dto.vendor;

import java.util.UUID;

import app.restgourmet.api.commondata.models.PaymentTerm;
import lombok.Data;

@Data
public class VendorDto {
  private UUID partnerId;

  private PaymentTerm paymentTerm;
}
