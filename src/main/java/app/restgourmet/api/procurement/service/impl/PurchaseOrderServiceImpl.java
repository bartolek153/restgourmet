package app.restgourmet.api.procurement.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import app.restgourmet.api.commondata.repository.CurrencyRepository;
import app.restgourmet.api.commondata.repository.PaymentTermRepository;
import app.restgourmet.api.commondata.service.impl.GlobalParametersServiceImpl;
import app.restgourmet.api.procurement.dto.purchaseorder.CreatePurchaseOrderDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListDto;
import app.restgourmet.api.procurement.dto.purchaseorder.PurchaseOrderListFiltersDto;
import app.restgourmet.api.procurement.mappers.PurchaseOrderMapper;
import app.restgourmet.api.procurement.models.PurchaseOrder;
import app.restgourmet.api.procurement.repository.PurchaseOrderRepository;
import app.restgourmet.api.procurement.repository.VendorRepository;
import app.restgourmet.api.procurement.repository.specifications.PurchaseOrderSpecification;
import app.restgourmet.api.procurement.service.spec.PurchaseOrderService;
import app.restgourmet.api.shared.models.parameters.GlobalParameters;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

  private final PurchaseOrderRepository purchaseOrderRepository;
  private final VendorRepository vendorRepository;
  private final CurrencyRepository currencyRepository;
  private final PaymentTermRepository paymentRepositoryRepository;
  private final GlobalParametersServiceImpl globalParametersServiceImpl;

  @Autowired
  private PurchaseOrderMapper purchaseOrderMapper;

  public PurchaseOrderServiceImpl(
      PurchaseOrderRepository purchaseOrderRepository,
      VendorRepository vendorRepository,
      CurrencyRepository currencyRepository,
      PaymentTermRepository paymentTermRepository,
      GlobalParametersServiceImpl globalParametersServiceImpl) {
    this.purchaseOrderRepository = purchaseOrderRepository;
    this.vendorRepository = vendorRepository;
    this.currencyRepository = currencyRepository;
    this.paymentRepositoryRepository = paymentTermRepository;
    this.globalParametersServiceImpl = globalParametersServiceImpl;
  }

  @Override
  public PagedModel<PurchaseOrderListDto> list(PageRequest pagReq, PurchaseOrderListFiltersDto filters) {
    Specification<PurchaseOrder> spec = PurchaseOrderSpecification.filterBy(filters);
    Page<PurchaseOrder> page = purchaseOrderRepository.findAll(spec, pagReq);
    return new PagedModel<>(page.map(purchaseOrderMapper::toListDto));
  }

  @Override
  public UUID create(CreatePurchaseOrderDto dto) {
    PurchaseOrder order = purchaseOrderMapper.toEntity(dto);
    Optional<GlobalParameters> gparams = globalParametersServiceImpl.getActive();

    if (dto.getVendorId() != null) {
      
    }

    if (dto.getCurrencyId() != null) {
      if (!currencyRepository.existsById(dto.getCurrencyId())) {
        // throw
      }
    } else {
      if (!gparams.isPresent() || gparams.get().getDefaultCurrency() == null) {
        order.setCurrency(gparams.get().getDefaultCurrency());
      }
    }

    if (dto.getPaymentTermsId() != null) {

    } else {

    }

    return purchaseOrderRepository.save(order).getId();
  }
}
