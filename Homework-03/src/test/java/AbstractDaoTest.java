import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractDaoTest {
    protected static PostgreSQLContainer postgres;

    @BeforeAll
    static void setup() {
        postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:18"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");
        postgres.start();
    }

    @AfterAll
    static void tearDown() {
        if (postgres != null) {
            postgres.stop();
        }
    }

    protected static String getJdbcUrl() {
        return postgres.getJdbcUrl();
    }

    protected static String getUsername() {
        return postgres.getUsername();
    }

    protected static String getPassword() {
        return postgres.getPassword();
    }
}
