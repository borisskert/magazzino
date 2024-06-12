package de.borisskert.springboot.liquibaseexample.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCreateProduct() throws Exception {
        Product product = new Product();
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

    @AfterEach
    void cleanup() throws Exception {
        productRepository.deleteAll();
    }
}
