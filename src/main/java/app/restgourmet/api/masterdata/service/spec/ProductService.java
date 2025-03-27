package app.restgourmet.api.masterdata.service.spec;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import app.restgourmet.api.masterdata.dto.product.CreateProductDto;
import app.restgourmet.api.masterdata.dto.product.EditProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductDto;
import app.restgourmet.api.masterdata.dto.product.ProductListDto;
import app.restgourmet.api.masterdata.dto.product.ProductListFiltersDto;

public interface ProductService {
  PagedModel<ProductListDto> list(PageRequest pageReq, ProductListFiltersDto filters);

  ProductDto getOne(UUID id);

  UUID create(CreateProductDto dto);

  void edit(UUID id, EditProductDto dto);

  void delete(UUID id);
}
