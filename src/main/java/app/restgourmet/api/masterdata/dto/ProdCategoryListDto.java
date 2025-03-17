package app.restgourmet.api.masterdata.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProdCategoryListDto {
  private UUID id;
  private String description; 
}