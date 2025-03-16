package app.restgourmet.api.inventoryhandling.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProdFamilyListDto {
  private UUID id;
  private String description;
  private UUID categoryId;
}