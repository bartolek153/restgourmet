package app.restgourmet.api.shared.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CreatedByDataDto {
  private UUID id;
  private String name;
}