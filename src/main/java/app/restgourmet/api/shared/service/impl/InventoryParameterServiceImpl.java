package app.restgourmet.api.shared.service.impl;

import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.repository.InventoryParametersRepository;
import app.restgourmet.api.shared.models.parameters.InventoryParameters;
import app.restgourmet.api.shared.service.spec.BaseParameterService;

@Service
public class InventoryParameterServiceImpl extends BaseParameterService<InventoryParameters> {

  public InventoryParameterServiceImpl(InventoryParametersRepository parameterRepository) {
    super(parameterRepository);
  }

  @Override
  protected InventoryParameters cloneParam(InventoryParameters param) {
    InventoryParameters newParam = new InventoryParameters();
    newParam.setStockTracingMandatory(param.isStockTracingMandatory());
    return newParam;
  }
}
