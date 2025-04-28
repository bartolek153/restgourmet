package app.restgourmet.api.shared.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class ErrorObject {
  private String message;
  private String details;
  private Map<String, List<String>> errors;

  @Builder.Default
  private Date timestamp = new Date();
}
