// package app.restgourmet.api.repository;

// import java.util.Optional;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.test.context.junit4.SpringRunner;

// import app.restgourmet.api.usermanagement.enums.UserRole;
// import app.restgourmet.api.usermanagement.models.UserEntity;
// import app.restgourmet.api.usermanagement.repository.UserRepository;
// import app.restgourmet.api.usermanagement.repository.projections.UserListProjection;

// @RunWith(SpringRunner.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
// public class UserRepositoryTests {

//   @Autowired
//   private UserRepository userRepository;

//   // @Autowired
//   // private TestEntityManager entityManager;

//   @BeforeEach
//   public void setUp() {
//     // Arrange
//     UserEntity user1 = new UserEntity(
//         "John Doe",
//         "johnDoe123",
//         "johndoe@example.com",
//         "password",
//         true,
//         UserRole.VIEWER,
//         null,
//         null);

//     UserEntity user2 = new UserEntity(
//         "Admin",
//         "admin",
//         "admin@example.com",
//         "password",
//         true,
//         UserRole.ADMIN,
//         null,
//         null);

//     userRepository.save(user1);
//     userRepository.save(user2);
//   }

//   @Test
//   public void findByExistingNickname_ReturnsUser() {
//     String nickname = "johnDoe123";

//     Optional<UserEntity> user = userRepository.findByNicknameOrEmail(nickname, null);

//     Assertions.assertTrue(user.isPresent());
//     Assertions.assertEquals(nickname, user.get().getNickname());
//   }

//   @Test
//   public void findByExistingEmail_ReturnsUser() {
//     String email = "admin@example.com";

//     Optional<UserEntity> user = userRepository.findByNicknameOrEmail(null, email);

//     Assertions.assertTrue(user.isPresent());
//     Assertions.assertEquals(email, user.get().getEmail());
//   }

//   @Test
//   public void findByNonExistingNickname_ReturnsNull() {
//     String nickname = "nonExistentNickname";

//     Optional<UserEntity> user = userRepository.findByNicknameOrEmail(nickname, null);

//     Assertions.assertFalse(user.isPresent());
//   }

//   @Test
//   public void findByNonExistingEmail_ReturnsNull() {
//     String email = "nonexistent@example.com";

//     Optional<UserEntity> user = userRepository.findByNicknameOrEmail(null, email);

//     Assertions.assertFalse(user.isPresent());
//   }

//   @Test
//   public void existsByExistingNickname_ReturnsTrue() {
//     String nickname = "johnDoe123";

//     boolean exists = userRepository.existsByNickname(nickname);

//     Assertions.assertTrue(exists);
//   }

//   @Test
//   public void existsByExistingEmail_ReturnsTrue() {
//     String email = "admin@example.com";

//     boolean exists = userRepository.existsByEmail(email);

//     Assertions.assertTrue(exists);
//   }

//   @Test
//   public void existsByNonExistingNickname_ReturnsFalse() {
//     String nickname = "nonExistentNickname";

//     boolean exists = userRepository.existsByNickname(nickname);

//     Assertions.assertFalse(exists);
//   }

//   @Test
//   public void existsByNonExistingEmail_ReturnsFalse() {
//     String email = "nonexistent@example.com";

//     boolean exists = userRepository.existsByEmail(email);

//     Assertions.assertFalse(exists);
//   }

//   @Test
//   public void findUserListProjectionPagination_ReturnsPage() {
//     // Given: A pageable request with page size 1
//     Pageable pageable = PageRequest.of(0, 1);

//     // When: Finding users with pagination
//     Page<UserListProjection> page = userRepository.findAllBy(pageable);

//     // Then: Assert that the page is not empty and contains one user
//     Assertions.assertNotNull(page);
//     Assertions.assertTrue(page.hasContent());
//     Assertions.assertEquals(1, page.getSize());
//   }
// }
