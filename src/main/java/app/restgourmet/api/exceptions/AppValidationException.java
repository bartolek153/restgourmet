package app.restgourmet.api.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppValidationException extends RuntimeException {
  Map<String, List<String>> errors;
  
  public AppValidationException() {
    super();
    errors = new HashMap<>();
  }

  public AppValidationException(String message) {
    super(message);
    errors = new HashMap<>();
  }
  
  public AppValidationException(String field, String message) {
    super();
    errors = new HashMap<>();
    errors.put(field, List.of(message));
  }

  public void addError(String field, String message) {
    if (errors.containsKey(field)) {
      errors.get(field).add(message);
    } else {
      errors.put(field, List.of(message));
    }
  }

  public Map<String, List<String>> getErrors() {
    return errors;
  }
}
