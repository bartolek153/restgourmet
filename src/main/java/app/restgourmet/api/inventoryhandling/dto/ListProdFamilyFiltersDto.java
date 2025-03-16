package app.restgourmet.api.inventoryhandling.dto;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class ListProdFamilyFiltersDto {
  private String q;

  public boolean isEmpty() {
    return !StringUtils.hasText(q);
  }
}