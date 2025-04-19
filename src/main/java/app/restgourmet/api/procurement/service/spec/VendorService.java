package app.restgourmet.api.procurement.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.procurement.dto.vendor.VendorDto;
import app.restgourmet.api.procurement.dto.vendor.VendorListDto;
import app.restgourmet.api.procurement.dto.vendor.VendorListFiltersDto;

public interface VendorService {
  PagedModel<VendorListDto> list(PageRequest pageReq, VendorListFiltersDto filters);

  VendorDto getOne(UUID id);

  UUID create(VendorDto dto);

  void edit(UUID id, VendorDto dto);

  void delete(UUID id);
}
