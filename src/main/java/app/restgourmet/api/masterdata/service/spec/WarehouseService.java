package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.warehouse.WarehouseDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListDto;
import app.restgourmet.api.masterdata.dto.warehouse.WarehouseListFiltersDto;

public interface WarehouseService {
  PagedModel<WarehouseListDto> list(PageRequest pageReq, WarehouseListFiltersDto filters);

  WarehouseDto getOne(UUID id);

  UUID create(WarehouseDto dto);

  void edit(UUID id, WarehouseDto dto);

  void delete(UUID id);
}
