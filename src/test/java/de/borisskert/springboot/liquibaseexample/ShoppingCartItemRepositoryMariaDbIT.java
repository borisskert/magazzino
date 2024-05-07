package de.borisskert.springboot.liquibaseexample;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ShoppingCartItemRepositoryMariaDbIT extends ShoppingCartItemRepositoryTest {
    @Container
    public static MariaDBContainer<?> databaseContainer = new MariaDBContainer<>(
            DockerImageName.parse("mariadb:11.3")
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
        registry.add("spring.datasource.driver-class-name", () -> "org.mariadb.jdbc.Driver");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MariaDBDialect");
    }
}
