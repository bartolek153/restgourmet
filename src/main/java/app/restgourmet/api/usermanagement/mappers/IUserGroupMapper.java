package app.restgourmet.api.usermanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.dto.group.CreateUserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.EditUserGroupDto;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserGroup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserGroupMapper {
  @Mapping(target = "permissions", qualifiedByName = "mapPermSetToStrList")
  @Mapping(target = "createdBy", source = "createdBy.name")
  @Mapping(target = "updatedBy", source = "updatedBy.name")
  UserGroupDto toDto(UserGroup ent);

  @Mapping(target = "createdBy", source = "createdBy.name")
  UserGroupListDto toListDto(UserGroup ent);

  @Mapping(target = "permissions", ignore = true)
  UserGroup createToEntity(CreateUserGroupDto dto);

  @Mapping(target = "permissions", ignore = true)
  void updateEntity(EditUserGroupDto dto, @MappingTarget UserGroup ent);

  @Named("mapPermSetToStrList")
  static List<String> mapPermSetToStrList(Set<Permission> permissions) {
    List<String> result = new ArrayList<>();

    for (Permission perm : permissions) {
      result.add(perm.getName());
    }

    return result;
  }
}
