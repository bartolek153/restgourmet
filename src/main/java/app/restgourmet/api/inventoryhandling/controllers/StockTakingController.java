package app.restgourmet.api.inventoryhandling.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.restgourmet.api.inventoryhandling.dto.stocktaking.CreateStockTakingDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListDto;
import app.restgourmet.api.inventoryhandling.dto.stocktaking.StockTakingListFiltersDto;
import app.restgourmet.api.inventoryhandling.service.spec.StockTakingService;
import app.restgourmet.api.shared.controller.CustomPageRequest;
import app.restgourmet.api.usermanagement.security.UserDetailsImpl;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stock/takings")
@Tag(name = "Stock Taking", description = "Stock takings endpoints")
public class StockTakingController {

  private final StockTakingService stockTakingService;

  public StockTakingController(StockTakingService stockTakingService) {
    this.stockTakingService = stockTakingService;
  }

  @GetMapping
  public ResponseEntity<PagedModel<StockTakingListDto>> listTakings(
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_PAGE) final Integer page,
      @RequestParam(defaultValue = AppConstants.Pagination.DEFAULT_SIZE) final Integer size,
      @RequestParam(defaultValue = "ASC") final Direction order,
      @RequestParam(defaultValue = "startDate") final String sort,
      @ParameterObject final StockTakingListFiltersDto filters) {
    return ResponseEntity.ok(stockTakingService.list(CustomPageRequest.of(page, size, order, sort), filters));
  }

  @PostMapping
  public ResponseEntity<UUID> createTaking(@RequestBody @Valid CreateStockTakingDto dto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
    
    UUID id = stockTakingService.create(dto, ud.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }
}
