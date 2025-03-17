package app.restgourmet.api.masterdata.dto;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class ProdGroupListFiltersDto {
  private String q;

  public boolean isEmpty() {
    return !StringUtils.hasText(q);
  }
}