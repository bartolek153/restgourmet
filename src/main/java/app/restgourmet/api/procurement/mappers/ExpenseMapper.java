package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.procurement.dto.expense.CreateExpenseDto;
import app.restgourmet.api.procurement.dto.expense.EditExpenseDto;
import app.restgourmet.api.procurement.dto.expense.ExpenseDto;
import app.restgourmet.api.procurement.dto.expense.ExpenseListDto;
import app.restgourmet.api.procurement.models.Expense;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseMapper {
  ExpenseDto toDto(Expense ent);

  ExpenseListDto toListDto(Expense ent);

  Expense toEntity(CreateExpenseDto dto);

  void updateEntity(EditExpenseDto dto, @MappingTarget Expense ent);
}
