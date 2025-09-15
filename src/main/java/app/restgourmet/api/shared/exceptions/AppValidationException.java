package app.restgourmet.api.shared.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AppValidationException extends RuntimeException {
  Map<String, List<Object>> errors  = new HashMap<>();

  public AppValidationException() {
    super();
  }

  public AppValidationException(String message) {
    super(message);
  }

  public AppValidationException(String field, String message) {
    super();
    errors.put(field, List.of(message));
  }

  public AppValidationException(String field, Map<String, String> errorMap ) {
    super();
    errors.put(field, List.of(errorMap));
  }

  public void addError(String field, String message) {
    if (errors.containsKey(field)) {
      errors.get(field).add(message);
    } else {
      errors.put(field, List.of(message));
    }
  }

  public Map<String, List<Object>> getErrors() {
    return errors;
  }
}
