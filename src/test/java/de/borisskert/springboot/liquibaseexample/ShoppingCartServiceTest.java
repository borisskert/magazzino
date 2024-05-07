package de.borisskert.springboot.liquibaseexample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    private Person person;

    private List<Product> products;

    @BeforeEach
    void setup() throws Exception {
        person = setupPerson();
        products = setupProducts();
    }

    @Test
    void shouldCreateNewShoppingCartIfNotPresent() throws Exception {
        ShoppingCart shoppingCart = shoppingCartService.retrieveShoppingCart(person.getId());

        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getId());
        assertEquals(person.getId(), shoppingCart.getCustomerId());
        assertFalse(shoppingCart.isCheckedOut());
    }

    @Test
    void shouldRetrieveSameShoppingCartAfterCreation() throws Exception {
        ShoppingCart shoppingCart1 = shoppingCartService.retrieveShoppingCart(person.getId());
        ShoppingCart shoppingCart2 = shoppingCartService.retrieveShoppingCart(person.getId());

        assertEquals(shoppingCart1.getId(), shoppingCart2.getId());
    }

    @Test
    void shouldAddItemToNewShoppingCart() throws Exception {
        shoppingCartService.addItemToCart(
                person.getId(), products.getFirst().getId(), 2L
        );

        ShoppingCart shoppingCart = shoppingCartService.retrieveShoppingCart(person.getId());

        List<ShoppingCartItem> items = shoppingCartItemRepository.findAll();

        assertThat(items).hasSize(1);

        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getId());
        assertEquals(person.getId(), shoppingCart.getCustomerId());
        assertFalse(shoppingCart.isCheckedOut());
    }

    @Test
    void shouldAddItemsToShoppingCart() throws Exception {
        shoppingCartService.addItemToCart(
                person.getId(), products.getFirst().getId(), 2L
        );

        shoppingCartService.addItemToCart(
                person.getId(), products.getLast().getId(), 1L
        );

        ShoppingCart shoppingCart = shoppingCartService.retrieveShoppingCart(person.getId());

        List<ShoppingCartItem> items = shoppingCart.getItems();
        assertEquals(2, items.size());

        ShoppingCartItem item1 = items.getFirst();
        assertEquals(products.getFirst().getId(), item1.getProduct().getId());
        assertEquals(2L, item1.getQuantity());

        ShoppingCartItem item2 = items.getLast();
        assertEquals(products.getLast().getId(), item2.getProduct().getId());
        assertEquals(1L, item2.getQuantity());
    }

    @AfterEach
    void cleanup() throws Exception {
        shoppingCartItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        personRepository.deleteAll();
    }

    private Person setupPerson() {
        Person person = new Person();
        person.setUsername("johndoe");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setRole(Person.Role.USER);
        person.setBirthdate(LocalDate.parse("1980-11-15"));

        return personRepository.save(person);
    }

    private List<Product> setupProducts() {
        Product product1 = new Product();
        product1.setNumber("P-123456");
        product1.setName("My Test Product 1");
        product1.setDescription("This is a test product");
        product1.setPrice(123.45);

        Product product2 = new Product();
        product2.setNumber("P-123457");
        product2.setName("My Test Product 2");
        product2.setDescription("This is another test product");
        product2.setPrice(234.56);

        Iterable<Product> savedProducts = productRepository.saveAll(List.of(
                product1,
                product2
        ));

        Spliterator<Product> spliterator = Spliterators.spliteratorUnknownSize(savedProducts.iterator(), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false)
                .collect(Collectors.toList());
    }
}
