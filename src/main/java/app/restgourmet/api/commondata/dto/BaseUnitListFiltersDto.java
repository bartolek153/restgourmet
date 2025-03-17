package app.restgourmet.api.commondata.dto;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class BaseUnitListFiltersDto {
  private String q;

  public boolean isEmpty() {
    return !StringUtils.hasText(q);
  }
}
