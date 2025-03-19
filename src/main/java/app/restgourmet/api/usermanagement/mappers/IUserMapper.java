package app.restgourmet.api.usermanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.dto.ImageDto;
import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPicture")
  UserEntity createDtoToEntity(CreateUserDto dto);

  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPicture")
  void updateEntity(EditUserDto dto, @MappingTarget UserEntity entity);

  UserEntity editProfileDtoToEntity(EditProfileDto dto);

  UserEntity entityToEntity(UserEntity entity);

  UserListDto entityToListDto(UserEntity entities);

  // @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissionsToStrings")
  @Mapping(target = "picture", source = "picture", qualifiedByName = "mapPictureToDto")
  UserDto toDto(UserEntity entity);

  @Named("mapPermissionsToStrings")
  static List<String> mapPermissionsToStrings(List<Permission> permissions) {
    return permissions != null ? permissions.stream()
        .map(Permission::getName)
        .collect(Collectors.toList()) : new ArrayList<>();
  }

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
    if (StringUtils.hasText(pic)){ 
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
}
