package app.restgourmet.api.usermanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import app.restgourmet.api.usermanagement.dto.role.CreateRoleDto;
import app.restgourmet.api.usermanagement.dto.role.EditRoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
  @Mapping(target = "permissions", qualifiedByName = "mapPermSetToStrList")
  @Mapping(target = "createdBy", source = "createdBy.name")
  @Mapping(target = "updatedBy", source = "updatedBy.name")
  RoleDto toDto(Role ent);

  @Mapping(target = "createdBy", source = "createdBy.name")
  RoleListDto toListDto(Role ent);

  @Mapping(target = "permissions", ignore = true)
  Role createToEntity(CreateRoleDto dto);

  @Mapping(target = "permissions", ignore = true)
  void updateEntity(EditRoleDto dto, @MappingTarget Role ent);

  @Named("mapPermSetToStrList")
  static List<String> mapPermSetToStrList(Set<Permission> permissions) {
    List<String> result = new ArrayList<>();

    for (Permission perm : permissions) {
      result.add(perm.getName());
    }

    return result;
  }
}
