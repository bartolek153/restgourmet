package app.restgourmet.api.financials.dto.expensenature;

import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseNatureListDto {
  private UUID id;
  private String name;
  private String description;
}