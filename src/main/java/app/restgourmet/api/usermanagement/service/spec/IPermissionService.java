package app.restgourmet.api.usermanagement.service.spec;

import java.util.List;

import app.restgourmet.api.usermanagement.dto.permission.PermissionCategoryDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionListDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;

public interface IPermissionService {
  List<PermissionCategoryDto> getPermissionCategories();
  
  List<PermissionListDto> getPermissions(PermissionCategory category);
}
