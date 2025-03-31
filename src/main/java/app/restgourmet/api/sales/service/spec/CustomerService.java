package app.restgourmet.api.sales.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.sales.dto.CustomerDto;
import app.restgourmet.api.sales.dto.CustomerListDto;
import app.restgourmet.api.sales.dto.CustomerListFiltersDto;

public interface CustomerService {
    PagedModel<CustomerListDto> list(PageRequest pageReq, CustomerListFiltersDto filters);

    CustomerDto getOne(UUID id);

    UUID create(UUID partnerId, CustomerDto dto);

    void edit(UUID partnerId, CustomerDto dto);

    void delete(UUID id);
}
