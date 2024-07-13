package de.borisskert.magazzino.product;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class ProductRepositoryMySqlIT extends ProductRepositoryTest {

    @Container
    public static MySQLContainer<?> databaseContainer = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.0.26")
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
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
    }
}
