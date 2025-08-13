package app.restgourmet.api.procurement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.procurement.dto.requestforquote.CreateRequestForQuoteDto;
import app.restgourmet.api.procurement.dto.requestforquote.RequestForQuoteDto;
import app.restgourmet.api.procurement.dto.requestforquote.RequestForQuoteListDto;
import app.restgourmet.api.procurement.models.RequestForQuote;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestForQuoteMapper {
  RequestForQuoteDto toDto(RequestForQuote ent);

  RequestForQuoteListDto toListDto(RequestForQuote ent);

  RequestForQuote toEntity(CreateRequestForQuoteDto dto);

  void updateEntity(RequestForQuoteDto dto, @MappingTarget RequestForQuote ent);
}
