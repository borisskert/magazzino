package de.borisskert.springboot.liquibaseexample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ShoppingCartItemRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private Person person;
    private ShoppingCart shoppingCart;
    private List<Product> products;

    @BeforeEach
    void setup() throws Exception {
        person = setupPerson();
        shoppingCart = setupShoppingCart();
        products = setupProducts();
    }

    @Test
    void shouldSaveCartItems() throws Exception {
        Product product1 = products.getFirst();
        ShoppingCartItem item1 = new ShoppingCartItem();
//        item1.setShoppingCartId(shoppingCart.getId());
        item1.setShoppingCart(shoppingCart);
//        item1.setProductId(product1.getId());
        item1.setProduct(product1);
        item1.setQuantity(2L);

        Product product2 = products.getLast();
        ShoppingCartItem item2 = new ShoppingCartItem();
        item2.setShoppingCart(shoppingCart);
//        item2.setShoppingCartId(shoppingCart.getId());
//        item2.setProductId(product2.getId());
        item2.setProduct(product2);
        item2.setQuantity(1L);

        List<ShoppingCartItem> savedItems = shoppingCartItemRepository.saveAll(List.of(
                item1,
                item2
        ));
        
        assertThat(savedItems).hasSize(2);
        assertThat(savedItems).extracting(ShoppingCartItem::getId).isNotNull();
//        assertThat(savedItems).extracting(ShoppingCartItem::getShoppingCartId).containsOnly(shoppingCart.getId());
        assertThat(savedItems).extracting(ShoppingCartItem::getProduct).extracting(Product::getId).containsOnly(product1.getId(), product2.getId());
        assertThat(savedItems).extracting(ShoppingCartItem::getQuantity).containsOnly(2L, 1L);

        List<ShoppingCartItem> items = shoppingCartItemRepository.findAll();
        assertThat(items).hasSize(2);
        assertThat(items).extracting(ShoppingCartItem::getId).isNotNull();
//        assertThat(items).extracting(ShoppingCartItem::getShoppingCartId).containsOnly(shoppingCart.getId());
//        assertThat(items).extracting(ShoppingCartItem::getProductId).containsOnly(product1.getId(), product2.getId());
        assertThat(savedItems).extracting(ShoppingCartItem::getProduct).extracting(Product::getId).containsOnly(product1.getId(), product2.getId());
        assertThat(items).extracting(ShoppingCartItem::getQuantity).containsOnly(2L, 1L);
        
        shoppingCartRepository.findById(shoppingCart.getId())
                .ifPresent(cart -> {
                    assertThat(cart.getItems()).hasSize(2);
                    assertThat(cart.getItems()).extracting(ShoppingCartItem::getId).containsOnly(item1.getId(), item2.getId());
                });
    }

    @AfterEach
    void cleanup() throws Exception {
        shoppingCartItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        personRepository.deleteAll();
        productRepository.deleteAll();
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

    private ShoppingCart setupShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(person.getId());
        shoppingCart.setCheckedOut(false);

        return shoppingCartRepository.save(shoppingCart);
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
