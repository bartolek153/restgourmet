package app.restgourmet.api.sales.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.sales.dto.SalesOrderDto;
import app.restgourmet.api.sales.dto.SalesOrderListDto;
import app.restgourmet.api.sales.dto.SalesOrderListFiltersDto;
import app.restgourmet.api.sales.enums.OrderStatus;

public interface SalesOrderService {
    PagedModel<SalesOrderListDto> list(PageRequest pageReq, SalesOrderListFiltersDto filters);

    SalesOrderDto getOne(UUID id);

    UUID create(SalesOrderDto dto);

    void edit(UUID id, SalesOrderDto dto);

    void delete(UUID id);

    void updateStatus(UUID id, OrderStatus status);
}
