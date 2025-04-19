package app.restgourmet.api.procurement.dto.expensecategory;

import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseCategoryListDto {
  private UUID id;
  private String name;
  private String description;
}