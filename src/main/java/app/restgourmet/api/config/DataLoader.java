package app.restgourmet.api.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.commondata.models.BaseUnit;
import app.restgourmet.api.commondata.repository.BaseUnitRepository;
import app.restgourmet.api.commondata.service.impl.GlobalParametersServiceImpl;
import app.restgourmet.api.masterdata.repository.UnitMeasurementRepository;
import app.restgourmet.api.shared.models.parameters.GlobalParameters;
import app.restgourmet.api.usermanagement.enums.PermissionCategory;
import app.restgourmet.api.usermanagement.enums.UserType;
import app.restgourmet.api.usermanagement.models.Permission;
import app.restgourmet.api.usermanagement.models.UserEntity;
import app.restgourmet.api.usermanagement.repository.PermissionRepository;
import app.restgourmet.api.usermanagement.repository.UserRepository;
import app.restgourmet.api.utils.Permissions;

@Component
public class DataLoader implements CommandLineRunner {

  private final GlobalParametersServiceImpl globalParametersServiceImpl;

  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
  private final BaseUnitRepository baseUnitRepository;
  private final PermissionRepository permissionRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DataLoader(
      BaseUnitRepository baseUnitRepository,
      PermissionRepository permissionRepository,
      PasswordEncoder passwordEncoder,
      UnitMeasurementRepository unitMeasurementRepository,
      UserRepository userRepository,
      GlobalParametersServiceImpl globalParametersServiceImpl) {
    this.baseUnitRepository = baseUnitRepository;
    this.permissionRepository = permissionRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.globalParametersServiceImpl = globalParametersServiceImpl;
  }

  @Override
  public void run(String... args) throws Exception {
    globalParametersServiceImpl.getActive().ifPresentOrElse((p) -> {
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

    baseUnitRepository.saveAll(
        List.of(
            new BaseUnit("Kilogram", "kg"),
            new BaseUnit("Liter", "L"),
            new BaseUnit("Meter", "m")));

    // initialize parameters
    globalParametersServiceImpl.create(
        new GlobalParameters(
            true,
            null,
            "",
            true));
  }
}
