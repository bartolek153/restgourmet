package app.restgourmet.api.commondata.service.impl;

import org.springframework.stereotype.Service;

import app.restgourmet.api.shared.models.parameters.GlobalParameters;
import app.restgourmet.api.shared.repository.BaseParameterRepository;
import app.restgourmet.api.shared.service.spec.BaseParameterService;

@Service
public class GlobalParametersServiceImpl extends BaseParameterService<GlobalParameters> {
  public GlobalParametersServiceImpl(BaseParameterRepository<GlobalParameters> parameterRepository) {
    super(parameterRepository);
  }

  @Override
  protected GlobalParameters cloneParam(GlobalParameters param) {
    GlobalParameters newParam = new GlobalParameters();
    newParam.setInitializedDb(param.isInitializedDb());
    return newParam;
  }
}
