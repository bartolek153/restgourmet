package app.restgourmet.api.exceptions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Value("${spring.profiles.active:default}")
  private String activeProfile;

  /**
   * Handles custom not found exception
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorObject> handleResourceNotFound(ResourceNotFoundException e) {
    ErrorObject err = ErrorObject.builder()
        .message(e.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  /**
   * Handles exception thrown when a user is not found
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorObject> handleUsernameNotFound(BadCredentialsException e) {
    ErrorObject err = ErrorObject.builder()
        .message(e.getMessage())
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  /**
   * Handles custom bad request exception
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorObject> handleBadRequest(BadRequestException e) {
    ErrorObject err = ErrorObject.builder()
        .message(e.getMessage())
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  /**
   * Handles jakarta validation errors.
   * Creates a dictionary of fields (keys) and its following errors as a list
   * (values)
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorObject> handleConstraintViolation(ConstraintViolationException e) {
    // Group errors by field and collect messages
    Map<String, List<String>> errors = e.getConstraintViolations()
        .stream()
        .collect(Collectors.groupingBy(
            violation -> violation.getPropertyPath().toString(), // Field name
            Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList()) // Error messages
        ));

    ErrorObject err = ErrorObject.builder()
        .message("Cannot proceed due to validation errors")
        .errors(errors)
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  @ExceptionHandler(AppValidationException.class)
  public ResponseEntity<ErrorObject> handleCustomValidation(AppValidationException e) {
    ErrorObject err = ErrorObject.builder()
        .message("Cannot proceed due to validation errors")
        .errors(e.getErrors())
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  /**
   * Handles bad payloads in requests.
   * Required to display error messages
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.groupingBy(
            FieldError::getField, // Get field name
            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList()) // Collect error messages
        ));

    ErrorObject err = ErrorObject.builder()
        .message("Cannot proceed due to validation errors")
        .errors(errors)
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  /**
   * Handles missing params in requests.
   * Required to display error messages
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorObject> handleParamError(MissingServletRequestParameterException e) {
    ErrorObject err = ErrorObject.builder()
        .message(e.getMessage())
        .build();

    return ResponseEntity.badRequest().body(err);
  }

  /**
   * Handles any other exception (not listed above) as an internal server error
   *
   * BUG: this handler catches every exception, even Spring-related Exceptions,
   * which is not expected
   *
   */
  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<ErrorObject> handleGenericError(Exception e) {
  // String det = null;

  // if ("dev".equals(activeProfile)) {
  // det = e.getMessage();
  // }

  // ErrorObject err = ErrorObject.builder()
  // .message("An error occurred while processing the request. Please, try again
  // later.")
  // .details(det)
  // .build();

  // return ResponseEntity.internalServerError().body(err);
  // }
}
