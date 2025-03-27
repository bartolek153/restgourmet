package app.restgourmet.api.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.enums.UserType;
import app.restgourmet.api.usermanagement.models.Parameter;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.ParameterRepository;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.Permissions;

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
    parameterRepository.findByOptionKey(AppConstants.Parameters.DB_INITIALIZED_KEY).ifPresentOrElse((p) -> {
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
            new Permission(Permissions.READ_USERS, null, null, PermissionCategory.USER),
            new Permission(Permissions.WRITE_USERS, null, null, PermissionCategory.USER),
            new Permission(Permissions.READ_PRODUCT, null, null, PermissionCategory.PRODUCT),
            new Permission(Permissions.WRITE_PRODUCT, null, null, PermissionCategory.PRODUCT),
            new Permission(Permissions.READ_INVENTORY, null, null, PermissionCategory.INVENTORY),
            new Permission(Permissions.WRITE_INVENTORY, null, null, PermissionCategory.INVENTORY)));

    // initialize users
    UserEntity admin = new UserEntity(
        "Admin",
        "admin",
        "admin@admin.com",
        passwordEncoder.encode("admin"),
        true,
        UserType.ADMIN,
        null,
        null,
        null);

    userRepository.save(admin);

    // initialize parameters
    parameterRepository.saveAll(
        List.of(
            new Parameter(AppConstants.Parameters.DB_INITIALIZED_KEY, "true")));
  }
}
