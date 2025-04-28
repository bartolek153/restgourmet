package app.restgourmet.api.shared.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import app.restgourmet.api.utils.AppConstants;

public class CustomPageRequest {
  public static PageRequest of(int page, int size, Sort sort) {
    int validatedSize = Math.min(size, AppConstants.Pagination.MAX_PAGE_SIZE); // enforce a limit
    return PageRequest.of(page, validatedSize, sort);
  }

  public static PageRequest of(int page, int size, Direction dir, String... properties) {
    int validatedSize = Math.min(size, AppConstants.Pagination.MAX_PAGE_SIZE); // enforce a limit
    return PageRequest.of(page, validatedSize, Sort.by(dir, properties));
  }
}
