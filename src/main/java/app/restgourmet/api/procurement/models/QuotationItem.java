package app.restgourmet.api.procurement.models;

import app.restgourmet.api.masterdata.models.Product;
import app.restgourmet.api.masterdata.models.UnitMeasurement;

public class QuotationItem {
  private Quotation quotation;

  private Product product;

  private Double unitPrice;

  private Double quantity;

  private Double totalPrice;
}
