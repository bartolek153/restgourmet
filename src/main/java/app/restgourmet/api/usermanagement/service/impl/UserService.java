package app.restgourmet.api.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.restgourmet.api.commondata.service.impl.FileSystemStorageService;
import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.exceptions.AppValidationException;
import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.ListUserFiltersDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.mappers.IUserMapper;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.usermanagement.repository.specifications.UserSpec;
import app.restgourmet.api.usermanagement.security.UserDetailsImpl;
import app.restgourmet.api.usermanagement.service.spec.IUserService;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final PermissionRepository permissionRepository;
  private final NicknameGenerator nicknameGenerator;
  private final PasswordEncoder passwordEncoder;
  private final FileSystemStorageService storageService;

  @Autowired
  private IUserMapper userMapper;

  public UserService(
      UserRepository userRepository,
      NicknameGenerator nicknameGenerator,
      PasswordEncoder passwordEncoder,
      PermissionRepository permissionRepository,
      FileSystemStorageService storageService) {
    this.userRepository = userRepository;
    this.nicknameGenerator = nicknameGenerator;
    this.passwordEncoder = passwordEncoder;
    this.permissionRepository = permissionRepository;
    this.storageService = storageService;
  }

  @Override
  public PagedModel<UserListDto> listUsers(PageRequest pageRequest, ListUserFiltersDto filters) {
    Specification<UserEntity> spec = UserSpec.filterBy(filters);
    Page<UserListDto> res = userRepository.findAll(spec, pageRequest).map(userMapper::entityToListDto);
    return new PagedModel<>(res);
  }

  @Override
  @Cacheable(value = "users", key = "#id")
  public UserDto getUser(UUID id) {
    UserEntity user = userRepository.findById(id).orElseThrow(
        () -> new BadRequestException("User not found"));
    return userMapper.toDto(user);
  }

  @Override
  public CreateUserDto createUser(CreateUserDto data) {
    if (userRepository.existsByEmail(data.getEmail())) {
      throw new AppValidationException("email", "Already in use");
    }

    // convert list of string permissions to entities
    List<Permission> permissions = new ArrayList<>();

    for (String perm : data.getPermissions()) {
      Permission dbperm = permissionRepository.findByName(perm)
          .orElseThrow(() -> new BadRequestException("Permission not found"));
      permissions.add(dbperm);
    }

    if (StringUtils.hasText(data.getNickname())) {
      if (userRepository.existsByNickname(data.getNickname()))
        throw new AppValidationException("nickname", "Already in use");
    } else {
      data.setNickname(nicknameGenerator.generateNickname()); // if username is empty, generate a random one
    }

    UserEntity entity = userMapper.createDtoToEntity(data);

    if (entity.getPicture() != null && !storageService.fileExists(entity.getPicture())) {
      throw new BadRequestException("Picture not uploaded");
    }

    entity.setEnabled(false);
    // entity.setPermissions(permissions);
 
    userRepository.save(entity);

    return data;
  }

  @Override
  public EditUserDto editUser(UUID id, EditUserDto data) {
    UserEntity entity = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, id));

    // check if the email or username is already in use
    if (StringUtils.hasText(data.getEmail()) && !entity.getEmail().equals(data.getEmail())
        && userRepository.existsByEmail(data.getEmail())) {
      throw new AppValidationException("email", "Already in use");
    }

    if (StringUtils.hasText(data.getNickname()) && !entity.getNickname().equals(data.getNickname())
        && userRepository.existsByNickname(data.getNickname())) {
      throw new AppValidationException("nickname", "Already in use");
    }

    userMapper.updateEntity(data, entity);

    if (entity.getPicture() != null && !storageService.fileExists(entity.getPicture())) {
      throw new BadRequestException("Picture not uploaded");
    }

    // convert list of string permissions to entities
    if (data.getPermissions().size() > 0) {
      List<Permission> permissions = new ArrayList<>();

      for (String perm : data.getPermissions()) {
        Permission dbperm = permissionRepository.findByName(perm)
            .orElseThrow(() -> new BadRequestException("Permission not found"));
        permissions.add(dbperm);
      }
      // entity.setPermissions(permissions);
    }

    userRepository.save(entity);

    return data;
  }

  @Override
  @Transactional
  public void deleteUser(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();

    if (ud.getId().equals(id)) {
      throw new BadRequestException("You cannot delete yourself");
    }

    UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, id));

    // user.getPermissions().clear();
    userRepository.save(user);
    userRepository.deleteById(id);
  }

  @Override
  public void disableUser(UUID id) {
    userRepository.findById(id).ifPresent(user -> {
      user.setEnabled(false);
      userRepository.save(user);
    });
  }

  @Override
  public void enableUser(UUID id) {
    userRepository.findById(id).ifPresent(user -> {
      user.setEnabled(true);
      userRepository.save(user);
    });
  }

  @Override
  public UserEntity getProfile(Authentication auth) {
    UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
    return userRepository.findById(ud.getId()).orElse(null);
  }

  @Override
  public void editProfile(Authentication auth, EditProfileDto data) {
    UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
    userRepository.findById(ud.getId()).ifPresent(user -> {
      userMapper.editProfileDtoToEntity(data);
      userRepository.save(user);
    });
  }
}
