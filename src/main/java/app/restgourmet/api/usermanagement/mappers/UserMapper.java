package app.restgourmet.api.usermanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.dto.storage.ImageDto;
import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.models.UserGroup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPicture")
  UserEntity createDtoToEntity(CreateUserDto dto);

  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPicture")
  void updateEntity(EditUserDto dto, @MappingTarget UserEntity entity);

  UserEntity editProfileDtoToEntity(EditProfileDto dto);

  UserListDto toListDto(UserEntity entities);

  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPictureToDto")
  @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesIds")
  @Mapping(target = "groups", source = "groups", qualifiedByName = "groupsIds")
  UserDto toDto(UserEntity entity);

  @Named("mapPicture")
  static String mapPicture(List<ImageDto> pic) {
    if (pic != null && !pic.isEmpty()) {
      ImageDto img = pic.get(0);
      if (img.getResponse() != null) {
        return img.getResponse().getUrl();
      } else if (img.getUid() != null) {
        return img.getUid();
      }
    }
    return null;
  }

  @Named("mapPictureToDto")
  static List<ImageDto> mapPictureToDto(String pic) {
    if (StringUtils.hasText(pic)) {
      ImageDto image = new ImageDto();
      image.setUrl("http://localhost:5173/api/files/" + pic);
      image.setUid(pic);
      image.setName(pic);
      image.setType("image/jpeg");
      image.setSize(0);
      image.setPercentage(100);
      image.setStatus("done");
      return List.of(image);
    }

    return null;
  }

  @Named("rolesIds")
  static List<UUID> rolesToUUID(Set<Role> roles) {
    if (roles != null) {
      return roles.stream().map(Role::getId).collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  @Named("groupsIds")
  static List<UUID> groupsToUUID(Set<UserGroup> roles) {
    if (roles != null) {
      return roles.stream().map(UserGroup::getId).collect(Collectors.toList());
    }
    return new ArrayList<>();
  }
}
