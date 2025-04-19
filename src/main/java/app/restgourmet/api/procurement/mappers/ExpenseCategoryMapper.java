package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryDto;
import app.restgourmet.api.procurement.dto.expensecategory.ExpenseCategoryListDto;
import app.restgourmet.api.procurement.models.ExpenseCategory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseCategoryMapper {
  ExpenseCategoryDto toDto(ExpenseCategory ent);

  ExpenseCategoryListDto toListDto(ExpenseCategory ent);

  ExpenseCategory toEntity(ExpenseCategoryDto dto);

  void updateEntity(ExpenseCategoryDto dto, @MappingTarget ExpenseCategory ent);
}
