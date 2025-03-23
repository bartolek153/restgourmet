package app.restgourmet.api.usermanagement.security;

import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.usermanagement.service.spec.IUserService;
import app.restgourmet.api.utils.AppConstants;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final IUserService userService;
  // private final CacheManager cacheManager;

  public UserDetailsServiceImpl(
      UserRepository userRepository, 
      // CacheManager cacheManager, 
      IUserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
    // this.cacheManager = cacheManager;
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String identifier) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByNicknameOrEmail(identifier, identifier)
        .orElseThrow(() -> new UsernameNotFoundException(AppConstants.ErrorMessages.USER_LOGIN_BAD_CREDENTIALS));

    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    // cache to use it later
    // Cache cache = cacheManager.getCache(AppConstants.CacheKeys.USER_AUTHENTICATION);
    // if (cache != null) {
    //   cache.put(user.getId(), user);
    // }

    return userDetails;
  }

  public UserDetailsImpl loadUserById(UUID id) throws UsernameNotFoundException {
    UserEntity user = userService.findEntity(id)
        .orElseThrow(() -> new UsernameNotFoundException(AppConstants.ErrorMessages.USER_LOGIN_BAD_CREDENTIALS));
    return new UserDetailsImpl(user);
  }
}
