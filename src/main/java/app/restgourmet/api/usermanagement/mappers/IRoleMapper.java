package app.restgourmet.api.usermanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import app.restgourmet.api.usermanagement.dto.role.RoleDto;
import app.restgourmet.api.usermanagement.dto.role.RoleListDto;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    RoleDto toDto(Role ent);

    @Mapping(target = "createdBy", source = "createdBy.name")
    RoleListDto toListDto(Role ent);

    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleDto dto);

    @Mapping(target = "permissions", ignore = true)
    void updateEntity(RoleDto dto, @MappingTarget Role ent);
}
