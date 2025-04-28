package app.restgourmet.api.inventoryhandling.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.shared.controller.BaseParameterController;
import app.restgourmet.api.shared.models.parameters.InventoryParameters;
import app.restgourmet.api.shared.service.impl.InventoryParameterServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventory/parameters")
@Tag(name = "Inventory Parameter", description = "Inventory parameter endpoints")
public class InventoryParameterController extends BaseParameterController<InventoryParameters> {

  public InventoryParameterController(InventoryParameterServiceImpl parameterService) {
    super(parameterService);
  }
}
