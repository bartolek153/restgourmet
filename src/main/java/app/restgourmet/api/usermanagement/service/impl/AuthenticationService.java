package app.restgourmet.api.usermanagement.service.impl;

import app.restgourmet.api.exceptions.BadRequestException;
import app.restgourmet.api.usermanagement.dto.auth.LoginRequestDto;
import app.restgourmet.api.usermanagement.dto.auth.RegisterRequestDto;
import app.restgourmet.api.usermanagement.enums.UserType;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.usermanagement.security.JwtGenerator;
import app.restgourmet.api.usermanagement.security.UserDetailsImpl;
import app.restgourmet.api.usermanagement.security.UserDetailsServiceImpl;
import app.restgourmet.api.usermanagement.service.spec.IAuthenticationService;
import app.restgourmet.api.usermanagement.service.spec.INicknameGenerator;

import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtGenerator jwtGenerator;
  private final AuthenticationManager authenticationManager;
  private final INicknameGenerator nicknameGenerator;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtDecoder jwtDecoder;

  @Value("${restgourmet.api.jwt-cookie-name}")
  private String accessJwtCookie;

  @Value("${restgourmet.api.jwt-refresh-cookie-name}")
  private String refreshJwtCookie;

  public AuthenticationService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtGenerator jwtGenerator,
      AuthenticationManager authenticationManager,
      INicknameGenerator nicknameGenerator,
      UserDetailsServiceImpl userDetailsService,
      @Qualifier("refreshJwtDecoder") JwtDecoder jwtDecoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtGenerator = jwtGenerator;
    this.authenticationManager = authenticationManager;
    this.nicknameGenerator = nicknameGenerator;
    this.userDetailsService = userDetailsService;
    this.jwtDecoder = jwtDecoder;
  }

  /**
   * Register a new user.
   * 
   * @param request the registration request
   * @return a response entity with the result of the registration
   */
  public ResponseEntity<String> register(RegisterRequestDto request) {
    if (StringUtils.isBlank(request.getNickname())) {
      request.setNickname(nicknameGenerator.generateNickname());
    } else {
      if (userRepository.existsByNickname(request.getNickname())) {
        return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
      }
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
    }

    UserEntity user = request.toUserEntity();

    user.setType(UserType.ADMIN);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
  }

  /**
   * Authenticates the user with the provided login credentials and generates JWT
   * tokens for authentication.
   *
   * @param request the login request containing the user's identifier and
   *                password
   * @return a ResponseEntity containing the generated access and refresh tokens
   *         as cookies
   */
  public ResponseEntity<String> login(LoginRequestDto request) {
    if (StringUtils.isBlank(request.getIdentifier()) || StringUtils.isBlank(request.getPassword())) {
      throw new BadRequestException("Bad credentials");
    }

    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getIdentifier(),
            request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(auth);

    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

    String refreshToken = jwtGenerator.generateRefreshToken(userDetails);
    String accessToken = jwtGenerator.generateAccessToken(userDetails);

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,
            createJwtCookie(accessJwtCookie, accessToken, Duration.ofMinutes(15), "/api"))
        .header(HttpHeaders.SET_COOKIE,
            createJwtCookie(refreshJwtCookie, refreshToken, Duration.ofDays(7), "/api"))
        .body(accessToken);
  }

  /**
   * Generates a new access token using the provided refresh token.
   *
   * @param refreshToken the refresh token
   * @return a ResponseEntity containing the new access token as a cookie
   */
  public ResponseEntity<String> token(String refreshToken) {
    if (StringUtils.isBlank(refreshToken)) {
      return ResponseEntity.badRequest().body("Refresh token is empty");
    }

    Jwt jwt = null;

    try {
      jwt = jwtDecoder.decode(refreshToken);
    } catch (BadJwtException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());

    String newRefreshToken = jwtGenerator.generateRefreshToken(userDetails);
    String newAccessToken = jwtGenerator.generateAccessToken(userDetails);

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,
            createJwtCookie(refreshJwtCookie, newRefreshToken, Duration.ofDays(7), "/api"))
        .header(HttpHeaders.SET_COOKIE,
            createJwtCookie(accessJwtCookie, newAccessToken, Duration.ofMinutes(15), "/api"))
        .build();
  }

  /**
   * Logs out the user by clearing the access and refresh tokens.
   *
   * @return a ResponseEntity with the cleared cookies
   */
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cleanCookie(accessJwtCookie))
        .header(HttpHeaders.SET_COOKIE, cleanCookie(refreshJwtCookie))
        .build();
  }

  private String createJwtCookie(String name, String value, Duration duration, String path) {
    return ResponseCookie
        .from(name, value)
        .path(path)
        .maxAge(duration)

        .httpOnly(true)
        // .secure(true)
        // .sameSite("None")

        .build()
        .toString();
  }

  private String cleanCookie(String name) {
    return ResponseCookie
        .from(name, "")
        .path("/api/auth/token")
        .build()
        .toString();
  }
}
