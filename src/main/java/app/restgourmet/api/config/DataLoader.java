package app.restgourmet.api.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.restgourmet.api.usermanagement.enums.UserRole;
import app.restgourmet.api.usermanagement.models.Parameter;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.ParameterRepository;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.utils.AppConstants;
import jakarta.transaction.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
  private final UserRepository userRepository;
  private final PermissionRepository permissionRepository;
  private final ParameterRepository parameterRepository;
  private final PasswordEncoder passwordEncoder;

  public DataLoader(
      UserRepository userRepository,
      PermissionRepository permissionRepository,
      ParameterRepository parameterRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.permissionRepository = permissionRepository;
    this.parameterRepository = parameterRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    parameterRepository.findByKey(AppConstants.Parameters.DB_INITIALIZED_KEY).ifPresentOrElse((p) -> {
      logger.info("Database already initialized.");
    }, () -> {
      try {
        logger.info("Initializing database...");
        initializeDatabase();
        logger.info("Database successfuly initialized.");
      } catch (Exception e) {
        logger.error("Error initializing database: " + e.getMessage());
      }
    });
  }

  @Transactional
  private void initializeDatabase() {
    // initialize permissions
    permissionRepository.saveAll(
        List.of(
            new Permission(AppConstants.Permissions.READ_USERS),
            new Permission(AppConstants.Permissions.WRITE_USERS)));

    // initialize users
    UserEntity admin = new UserEntity(
        "Admin",
        "admin",
        "admin@admin.com",
        passwordEncoder.encode("admin"),
        true,
        UserRole.ADMIN,
        null,
        null);

    userRepository.save(admin);

    // initialize parameters
    parameterRepository.saveAll(
        List.of(
            new Parameter(AppConstants.Parameters.DB_INITIALIZED_KEY, "true")));
  }
}
