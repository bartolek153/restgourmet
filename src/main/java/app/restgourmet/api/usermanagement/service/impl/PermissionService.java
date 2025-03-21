package app.restgourmet.api.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.restgourmet.api.usermanagement.dto.permission.PermissionCategoryDto;
import app.restgourmet.api.usermanagement.dto.permission.PermissionListDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.service.spec.IPermissionService;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionCategoryDto> getPermissionCategories() {
        List<PermissionCategoryDto> categories = new ArrayList<>();

        for (PermissionCategory cat : PermissionCategory.values()) {
            categories.add(new PermissionCategoryDto(cat.toString()));
        }

        return categories;
    }

    @Override
    public List<PermissionListDto> getPermissions(PermissionCategory category) {
        return permissionRepository.findByCategory(category)
                .stream()
                .map((permission) -> new PermissionListDto(permission))
                .toList();
    }
}
