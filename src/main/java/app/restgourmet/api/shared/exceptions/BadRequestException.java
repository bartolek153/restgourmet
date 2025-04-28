package app.restgourmet.api.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(Class<?> clazz, String id) {
    super(String.format("Resource %s with id %s not found", clazz.getSimpleName(), id));
  }
}
