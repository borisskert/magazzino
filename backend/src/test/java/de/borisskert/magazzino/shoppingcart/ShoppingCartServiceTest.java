package de.borisskert.magazzino.shoppingcart;

import de.borisskert.magazzino.person.Person;
import de.borisskert.magazzino.person.PersonRepository;
import de.borisskert.magazzino.security.Role;
import de.borisskert.magazzino.product.Product;
import de.borisskert.magazzino.product.ProductRepository;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCart;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCartItem;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

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

        assertThat(shoppingCart).isNotNull();
        assertThat(shoppingCart.getId()).isNotNull();
        assertThat(shoppingCart.getCustomer()).extracting(Person::getId).isEqualTo(person.getId());
        assertThat(shoppingCart.isCheckedOut()).isFalse();
        assertThat(shoppingCart.getItems()).isEmpty();
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

        assertThat(shoppingCart).isNotNull();
        assertThat(shoppingCart.getId()).isNotNull();
        assertThat(shoppingCart.getCustomer()).extracting(Person::getId).isEqualTo(person.getId());
        assertThat(shoppingCart.isCheckedOut()).isFalse();
        assertThat(shoppingCart.getItems()).hasSize(1);
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

    @Test
    void shouldThrowWhenTryingToAddUnknownProductToCart() throws Exception {
        assertThatThrownBy(() -> shoppingCartService.addItemToCart(
                person.getId(), UUID.randomUUID(), 1L
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowWhenTryingToGetCartForUnknownPerson() throws Exception {
        assertThatThrownBy(() -> shoppingCartService.retrieveShoppingCart(Long.MAX_VALUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @AfterEach
    void cleanup() throws Exception {
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        personRepository.deleteAll();
    }

    private Person setupPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setRole(Role.EMPLOYEE);

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
