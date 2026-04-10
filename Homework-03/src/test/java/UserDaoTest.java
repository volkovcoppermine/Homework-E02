import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.volkov.hellotest.dao.UserDao;
import org.volkov.hellotest.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest extends AbstractDaoTest {
    private static SessionFactory sessionFactory;
    private static UserDao userDao;

    @BeforeAll
    static void initSessionFactory() {
        Configuration configuration = new Configuration()
                .configure("hibernate-test.cfg.xml") // отдельный конфиг для тестов!
                .setProperty("hibernate.connection.url", getJdbcUrl())
                .setProperty("hibernate.connection.username", getUsername())
                .setProperty("hibernate.connection.password", getPassword());
        sessionFactory = configuration.buildSessionFactory();
    }

    @BeforeAll
    static void setup() {
        userDao = new UserDao();
    }

    @AfterAll
    static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    @Order(1)
    void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setName("Иван");
        user.setEmail("ivan@mail.ru");
        user.setAge(30);
        user.setCreated_at(OffsetDateTime.now());

        assertTrue(userDao.create(user));
    }

    @Test
    @Order(2)
    void testGetById() {
        UserEntity user = new UserEntity();
        user.setName("Пётр");
        user.setEmail("petr@mail.ru");
        user.setAge(25);
        user.setCreated_at(OffsetDateTime.now());
        userDao.create(user);

        Optional<UserEntity> found = userDao.get(user.getId());
        assertTrue(found.isPresent());
        assertEquals("Пётр", found.get().getName());
    }

    @Test
    @Order(3)
    void testUpdate() {
        UserEntity user = new UserEntity();
        user.setName("Вася");
        user.setEmail("vasya@mail.ru");
        user.setAge(20);
        user.setCreated_at(OffsetDateTime.now());
        userDao.create(user);

        Optional<UserEntity> found = userDao.get(user.getId());
        assertTrue(found.isPresent());
        assertEquals("Вася", found.get().getName());

        UserEntity vas = found.get();
        vas.setName("Василий");
        assertTrue(userDao.update(vas));

        Optional<UserEntity> updated = userDao.get(vas.getId());
        assertTrue(updated.isPresent());
        assertEquals("Василий", updated.get().getName());
    }

    @Test
    @Order(4)
    void testDelete() {
        UserEntity user = new UserEntity();
        user.setName("Маша");
        user.setEmail("masha@mail.ru");
        user.setAge(32);
        user.setCreated_at(OffsetDateTime.now());
        assertTrue(userDao.create(user));
        long id = user.getId();
        assertTrue(userDao.delete(user));
        assertFalse(userDao.get(id).isPresent());
    }

    @Test
    @Order(5)
    void testGetAll() {
        createTestUsers();
        List<UserEntity> users = userDao.getAll();
        assertFalse(users.isEmpty());
    }

    private static void createTestUsers() {
        for (int i = 1; i <= 5; i++) {
            UserEntity user = new UserEntity();
            user.setName(String.format("Test %d", i));
            user.setEmail(String.format("test%d@mail.ru", i));
            user.setAge(20 + i);
            user.setCreated_at(OffsetDateTime.now());
            assertTrue(userDao.create(user));
        }
    }
}
