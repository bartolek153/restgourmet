package app.restgourmet.api.shared.service.spec;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.shared.dto.ParameterListFiltersDto;
import app.restgourmet.api.shared.exceptions.BadRequestException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.shared.models.parameters.BaseParameter;
import app.restgourmet.api.shared.repository.BaseParameterRepository;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

public abstract class BaseParameterService<T extends BaseParameter> {

  private final BaseParameterRepository<T> parameterRepository;

  public BaseParameterService(BaseParameterRepository<T> parameterRepository) {
    this.parameterRepository = parameterRepository;
  }

  public PagedModel<T> list(PageRequest pagReq, ParameterListFiltersDto filters) {
    return new PagedModel<>(parameterRepository.findAll(pagReq));
  }

  public T getOne(UUID id) {
    return getById(id);
  }

  public Optional<T> getActive() {
    return parameterRepository.findFirstByIsActiveTrue();
  }

  @Transactional
  public UUID create(T param) {
    return parameterRepository.save(param).getId();
  }

  @Transactional
  public void edit(UUID id, T param) {
    T oldParam = getById(id);
    oldParam.setIsActive(false);

    T newParam = cloneParam(param);
    newParam.setIsActive(true);

    parameterRepository.saveAll(List.of(oldParam, newParam));
  }

  @Transactional
  public void delete(UUID id) {
    T toDelete = getById(id);
    
    if (toDelete.isActive()) {
      throw new BadRequestException(ErrorMessages.PARAMETER_DELETE_IS_ACTIVE);
    }

    parameterRepository.deleteById(id);
  }

  private T getById(UUID id) {
    return parameterRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PARAMETER_NOT_FOUND));
  }

  // Must override this in specific services
  protected abstract T cloneParam(T param);
}
