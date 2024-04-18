package de.borisskert.springboot.liquibaseexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(UUID.fromString("c591b610-283e-47f6-9258-1189cd101fbc"));
        product.setNumber("P-123456");
        product.setName("My Test Product 1");
        product.setDescription("This is a test product");
        product.setPrice(123.45);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getNumber(), savedProduct.getNumber());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());

        Product foundProduct = productRepository.findById(savedProduct.getId()).orElseThrow();
        assertNotNull(foundProduct);
        assertNotNull(foundProduct.getId());
        assertEquals(product.getNumber(), foundProduct.getNumber());
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getDescription(), foundProduct.getDescription());
        assertEquals(product.getPrice(), foundProduct.getPrice());
    }
}
