package app.restgourmet.api.usermanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.models.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    RoleDto toDto(Role ent);

    RoleListDto toListDto(Role ent);

    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleDto dto);

    @Mapping(target = "permissions", ignore = true)
    void updateEntity(RoleDto dto, @MappingTarget Role ent);
}
