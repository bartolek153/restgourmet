package app.restgourmet.api.inventoryhandling.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.ListProdFamilyFiltersDto;
import app.restgourmet.api.inventoryhandling.dto.ProdFamilyDto;
import app.restgourmet.api.inventoryhandling.dto.ProdFamilyListDto;

@Service
public interface IProductFamilyService {
  public PagedModel<ProdFamilyListDto> list(PageRequest pageReq, ListProdFamilyFiltersDto filters);

  public ProdFamilyDto getOne(UUID id);

  public UUID create(ProdFamilyDto dto);

  public void edit(UUID id, ProdFamilyDto dto);

  public void delete(UUID id);
}
