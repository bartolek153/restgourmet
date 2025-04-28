package app.restgourmet.api.commondata.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.commondata.service.impl.GlobalParametersServiceImpl;
import app.restgourmet.api.shared.controller.BaseParameterController;
import app.restgourmet.api.shared.models.parameters.GlobalParameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/parameters")
@Tag(name = "Global Parameters", description = "Global parameters endpoints")
public class GlobalParametersController extends BaseParameterController<GlobalParameters> {
  
  public GlobalParametersController(GlobalParametersServiceImpl parameterService) {
    super(parameterService);
  }
}
