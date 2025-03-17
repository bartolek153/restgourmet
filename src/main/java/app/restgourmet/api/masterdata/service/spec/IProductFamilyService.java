package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.ProdFamilyDto;
import app.restgourmet.api.masterdata.dto.ProdFamilyListDto;
import app.restgourmet.api.masterdata.dto.ProdFamilyListFiltersDto;

public interface IProductFamilyService {
  public PagedModel<ProdFamilyListDto> list(PageRequest pageReq, ProdFamilyListFiltersDto filters);

  public ProdFamilyDto getOne(UUID id);

  public UUID create(ProdFamilyDto dto);

  public void edit(UUID id, ProdFamilyDto dto);

  public void delete(UUID id);
}
