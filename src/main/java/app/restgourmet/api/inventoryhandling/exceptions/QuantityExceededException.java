package app.restgourmet.api.inventoryhandling.exceptions;

import app.restgourmet.api.masterdata.models.UnitMeasurement;
import app.restgourmet.api.shared.exceptions.BadRequestException;

public class QuantityExceededException extends BadRequestException {
  public QuantityExceededException(String message) {
    super(message);
  }

  public QuantityExceededException(Double req, UnitMeasurement umReq, Double max, UnitMeasurement umMax) {
    super(String.format("Quantidade requisitada de %f (%s) excede o máximo definido de %f (%s)",
        req,
        umReq.toString(),
        max,
        umMax.toString()));
  }
}
