package app.restgourmet.api.masterdata.dto.family;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class ProdFamilyListFiltersDto {
  private String q;

  public boolean isEmpty() {
    return !StringUtils.hasText(q);
  }
}