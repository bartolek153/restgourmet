package app.restgourmet.api.usermanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.models.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
  
  @Mapping(target = "permissions", ignore = true)
  UserEntity createToEntity(CreateUserDto dto);

  @Mapping(target = "permissions", ignore = true)
  UserEntity editToEntity(EditUserDto dto);

  UserEntity editProfileToEntity(EditProfileDto dto);

  UserEntity entityToEntity(UserEntity entity);

  UserDto toEntityDto(UserEntity entities);
}
