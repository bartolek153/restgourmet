package app.restgourmet.api.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.restgourmet.api.usermanagement.dto.permission.PermissionListDto;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.service.spec.IPermissionService;

@Service
public class PermissionService implements IPermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  @Override
  public List<PermissionListDto> getPermissions(PermissionCategory category) {
    Map<PermissionCategory, List<String>> permissions = new HashMap<>();

    permissionRepository.findAll()
        .forEach(p -> {
          if (permissions.containsKey(p.getCategory())) {
            permissions.get(p.getCategory()).add(p.getName());
          } else {
            List<String> list = new ArrayList<>();
            list.add(p.getName());
            permissions.put(p.getCategory(), list);
          }
        });

    List<PermissionListDto> permissionList = new ArrayList<>();

    permissions.forEach((cat, perms) -> {
      permissionList
          .add(new PermissionListDto(
              cat.name(), 
              perms.stream().map((p) -> new PermissionListDto(p)).toList(), 
              true));
    });

    return permissionList;
  }
}
