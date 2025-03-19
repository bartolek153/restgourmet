package app.restgourmet.api.usermanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.usermanagement.dto.group.UserGroupDto;
import app.restgourmet.api.usermanagement.dto.group.UserGroupListDto;
import app.restgourmet.api.usermanagement.models.UserGroup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserGroupMapper {
  @Mapping(target = "permissions", ignore = true)
  UserGroupDto toDto(UserGroup ent);

  UserGroupListDto toListDto(UserGroup ent);

  @Mapping(target = "permissions", ignore = true)
  UserGroup toEntity(UserGroupDto dto);

  @Mapping(target = "permissions", ignore = true)
  void updateEntity(UserGroupDto dto, @MappingTarget UserGroup ent);
}
