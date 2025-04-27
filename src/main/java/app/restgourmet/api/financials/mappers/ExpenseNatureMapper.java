package app.restgourmet.api.financials.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureDto;
import app.restgourmet.api.financials.dto.expensenature.ExpenseNatureListDto;
import app.restgourmet.api.financials.models.ExpenseNature;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseNatureMapper {
  ExpenseNatureDto toDto(ExpenseNature ent);

  ExpenseNatureListDto toListDto(ExpenseNature ent);

  ExpenseNature toEntity(ExpenseNatureDto dto);

  void updateEntity(ExpenseNatureDto dto, @MappingTarget ExpenseNature ent);
}
