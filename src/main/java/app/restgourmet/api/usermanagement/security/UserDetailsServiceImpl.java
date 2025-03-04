package app.restgourmet.api.usermanagement.security;

import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String identifier) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByNicknameOrEmail(identifier, identifier)
        .orElseThrow(() -> new UsernameNotFoundException("User not found or invalid credentials"));

    return new UserDetailsImpl(user);
  }
}
