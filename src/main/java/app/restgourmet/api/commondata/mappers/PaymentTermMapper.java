package app.restgourmet.api.commondata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermDto;
import app.restgourmet.api.commondata.dto.paymentterm.PaymentTermListDto;
import app.restgourmet.api.commondata.models.PaymentTerm;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentTermMapper {
  PaymentTermDto toDto(PaymentTerm ent);

  PaymentTerm toEntity(PaymentTermDto dto);

  PaymentTermListDto toListDto(PaymentTerm ent);

  void updateEntity(PaymentTermDto dto, @MappingTarget PaymentTerm ent);
}
