package app.restgourmet.api.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidSecretException extends RuntimeException {
  public InvalidSecretException(String message) {
    super(message);
  }

  public InvalidSecretException() {
    super(ErrorMessages.SECRET_INVALID);
  }
}
