
package app.restgourmet.api.inventoryhandling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;
import app.restgourmet.api.inventoryhandling.mappers.StockTakingMapper;
import app.restgourmet.api.inventoryhandling.models.StockTaking;
import app.restgourmet.api.inventoryhandling.repository.StockTakingRepository;
import app.restgourmet.api.inventoryhandling.repository.specifications.StockTakingSpecification;
import app.restgourmet.api.inventoryhandling.service.spec.StockTakingService;

@Service
public class StockTakingServiceImpl implements StockTakingService {

    private final StockTakingRepository stockTakingRepository;

    @Autowired
    private StockTakingMapper stockTakingMapper;

    public StockTakingServiceImpl(StockTakingRepository stockTakingRepository) {
        this.stockTakingRepository = stockTakingRepository;
    }

    public PagedModel<StockTakingListDto> list(PageRequest pageReq, StockTakingListFiltersDto filters) {
        Specification<StockTaking> spec = StockTakingSpecification.filterBy(filters);
        Page<StockTakingListDto> res = stockTakingRepository.findAll(spec, pageReq)
                .map(stockTakingMapper::toListDto);

        return new PagedModel<>(res);
    }
}