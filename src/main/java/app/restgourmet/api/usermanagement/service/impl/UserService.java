package app.restgourmet.api.usermanagement.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.restgourmet.api.exceptions.StorageException;
import app.restgourmet.api.exceptions.AppValidationException;
import app.restgourmet.api.usermanagement.dto.user.CreateUserDto;
import app.restgourmet.api.usermanagement.dto.user.EditProfileDto;
import app.restgourmet.api.usermanagement.dto.user.EditUserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListFiltersDto;
import app.restgourmet.api.usermanagement.dto.user.UserDto;
import app.restgourmet.api.usermanagement.dto.user.UserListDto;
import app.restgourmet.api.usermanagement.mappers.IUserMapper;
import app.restgourmet.api.usermanagement.models.Role;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.models.UserGroup;
import app.restgourmet.api.usermanagement.repository.RoleRepository;
import app.restgourmet.api.usermanagement.repository.UserGroupRepository;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.usermanagement.repository.specifications.UserSpec;
import app.restgourmet.api.usermanagement.security.UserDetailsImpl;
import app.restgourmet.api.usermanagement.service.spec.IUserService;
import app.restgourmet.api.utils.AppConstants;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final UserGroupRepository userGroupRepository;
  private final RoleRepository roleRepository;

  private final NicknameGenerator nicknameGenerator;
  private final PasswordEncoder passwordEncoder;
  private final FileSystemStorageService storageService;

  @Autowired
  private IUserMapper userMapper;

  public UserService(
      UserRepository userRepository,
      NicknameGenerator nicknameGenerator,
      PasswordEncoder passwordEncoder,
      FileSystemStorageService storageService,
      UserGroupRepository userGroupRepository,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.nicknameGenerator = nicknameGenerator;
    this.passwordEncoder = passwordEncoder;
    this.storageService = storageService;
    this.userGroupRepository = userGroupRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public PagedModel<UserListDto> listUsers(PageRequest pageRequest, UserListFiltersDto filters) {
    Specification<UserEntity> spec = UserSpec.filterBy(filters);
    Page<UserListDto> res = userRepository.findAll(spec, pageRequest).map(userMapper::toListDto);
    return new PagedModel<>(res);
  }

  @Override
  // @Cacheable(value = "users", key = "#id")
  public UserDto getOne(UUID id) {
    UserEntity user = userRepository.findById(id).orElseThrow(
        () -> new BadRequestException(AppConstants.ErrorMessages.USER_NOT_FOUND));
    return userMapper.toDto(user);
  }

  @Override
  public CreateUserDto create(CreateUserDto data) {
    if (userRepository.existsByEmail(data.getEmail())) {
      throw new AppValidationException("email", AppConstants.ErrorMessages.USER_EMAIL_IN_USE);
    }

    if (StringUtils.hasText(data.getNickname())) {
      if (userRepository.existsByNickname(data.getNickname()))
        throw new AppValidationException("nickname", AppConstants.ErrorMessages.USER_NICKNAME_IN_USE);
    } else {
      data.setNickname(nicknameGenerator.generateNickname()); // if username is empty, generate a random one
    }

    UserEntity entity = userMapper.createDtoToEntity(data);

    if (entity.getPicture() != null && !storageService.fileExists(entity.getPicture())) {
      throw new StorageException(AppConstants.ErrorMessages.STORAGE_UPLOAD_ERROR);
    }

    entity.setEnabled(false);

    Set<UserGroup> groupRefs = new HashSet<>();
    for (UUID id : data.getGroupIds()) {
      groupRefs.add(userGroupRepository.getReferenceById(id));
    }

    Set<Role> roleRefs = new HashSet<>();
    for (UUID id : data.getRoleIds()) {
      roleRefs.add(roleRepository.getReferenceById(id));
    }

    entity.setGroups(groupRefs);
    entity.setRoles(roleRefs);
    userRepository.save(entity);

    return data;
  }

  @Override
  @Transactional
  // @CacheEvict(value = "users_auth", key = "#id")
  public EditUserDto edit(UUID id, EditUserDto data) {
    UserEntity entity = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.USER_NOT_FOUND));

    // check if the email or username is already in use
    if (StringUtils.hasText(data.getEmail()) && !entity.getEmail().equals(data.getEmail())
        && userRepository.existsByEmail(data.getEmail())) {
      throw new AppValidationException("email", AppConstants.ErrorMessages.USER_EMAIL_IN_USE);
    }

    if (StringUtils.hasText(data.getNickname()) && !entity.getNickname().equals(data.getNickname())
        && userRepository.existsByNickname(data.getNickname())) {
      throw new AppValidationException("nickname", AppConstants.ErrorMessages.USER_NICKNAME_IN_USE);
    }

    userMapper.updateEntity(data, entity);

    if (entity.getPicture() != null && !storageService.fileExists(entity.getPicture())) {
      throw new StorageException(AppConstants.ErrorMessages.STORAGE_UPLOAD_ERROR);
    }

    Set<UserGroup> groupRefs = new HashSet<>();
    for (UUID gid : data.getGroupIds()) {
      groupRefs.add(userGroupRepository.getReferenceById(gid));
    }

    Set<Role> roleRefs = new HashSet<>();
    for (UUID rid : data.getRoleIds()) {
      roleRefs.add(roleRepository.getReferenceById(rid));
    }

    entity.setGroups(groupRefs);
    entity.setRoles(roleRefs);
    userRepository.save(entity);

    return data;
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();

    if (ud.getId().equals(id)) {
      throw new BadRequestException("You cannot delete yourself");
    }

    UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ErrorMessages.USER_NOT_FOUND));

    user.getRoles().clear();
    user.getGroups().clear();
    userRepository.save(user);

    userRepository.deleteById(id);
  }

  @Override
  public void disable(UUID id) {
    userRepository.findById(id).ifPresent(user -> {
      user.setEnabled(false);
      userRepository.save(user);
    });
  }

  @Override
  public void enable(UUID id) {
    userRepository.findById(id).ifPresent(user -> {
      user.setEnabled(true);
      userRepository.save(user);
    });
  }

  @Override
  public UserEntity getAuthenticatedUser(Authentication auth) {
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

  @Override
  // @Cacheable(value = AppConstants.CacheKeys.USER_AUTHENTICATION, key = "#id")
  public Optional<UserEntity> findEntity(UUID id) {
    return userRepository.findById(id);
  }
}
