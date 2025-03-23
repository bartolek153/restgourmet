package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.address.AddressDto;
import app.restgourmet.api.masterdata.dto.address.AddressListDto;
import app.restgourmet.api.masterdata.dto.address.AddressListFiltersDto;

public interface AddressService {
  PagedModel<AddressListDto> list(PageRequest pageReq, AddressListFiltersDto filters);

  AddressDto getOne(UUID id);

  UUID create(AddressDto dto);

  void edit(UUID id, AddressDto dto);

  void delete(UUID id);

  AddressDto consultBrazilianAddress(String cep);
}
