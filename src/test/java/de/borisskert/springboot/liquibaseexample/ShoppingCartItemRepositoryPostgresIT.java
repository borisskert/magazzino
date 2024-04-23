package de.borisskert.springboot.liquibaseexample;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ShoppingCartItemRepositoryPostgresIT extends ShoppingCartItemRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16.2")
    )
            .withDatabaseName("testDB")
            .withUsername("user")
            .withPassword("password")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", databaseContainer::getJdbcUrl);
        registry.add("spring.datasource.password", databaseContainer::getPassword);
        registry.add("spring.datasource.username", databaseContainer::getUsername);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }
}
