package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListDto;
import app.restgourmet.api.masterdata.dto.businesspartner.BusinessPartnerListFiltersDto;

public interface BusinessPartnerService {
  PagedModel<BusinessPartnerListDto> list(PageRequest pageReq, BusinessPartnerListFiltersDto filters);

  BusinessPartnerDto getOne(UUID id);

  UUID create(BusinessPartnerDto dto);

  void edit(UUID id, BusinessPartnerDto dto);

  void delete(UUID id);
}
