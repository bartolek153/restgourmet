package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.ProdFamilyDto;
import app.restgourmet.api.masterdata.dto.ProdFamilyListDto;
import app.restgourmet.api.masterdata.dto.ProdFamilyListFiltersDto;

public interface IProductFamilyService {
  PagedModel<ProdFamilyListDto> list(PageRequest pageReq, ProdFamilyListFiltersDto filters);

  ProdFamilyDto getOne(UUID id);

  UUID create(ProdFamilyDto dto);

  void edit(UUID id, ProdFamilyDto dto);

  void delete(UUID id);
}
