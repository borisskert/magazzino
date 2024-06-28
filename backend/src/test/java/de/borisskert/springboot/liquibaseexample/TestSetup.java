package de.borisskert.springboot.liquibaseexample;

import de.borisskert.springboot.liquibaseexample.person.Person;
import de.borisskert.springboot.liquibaseexample.person.PersonRepository;
import de.borisskert.springboot.liquibaseexample.product.Product;
import de.borisskert.springboot.liquibaseexample.product.ProductRepository;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCart;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCartRepository;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Map;

import static io.restassured.parsing.Parser.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestSetup {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    protected Person person1, person2, person3, person4, person5;

    protected Product product1, product2, product3, product4, product5, product6, product7, product8, product9,
            product10, product11, product12, product13;

    protected ShoppingCart shoppingCart1, shoppingCart2, shoppingCart3, shoppingCart4, shoppingCart5;

    protected static final int EXPECTED_NUMBER_OF_PRODUCTS = 13;

    protected void setupEnvironment() throws Exception {
        setupRestAssured();
        setupCustomers();
        setupProducts();
        setupShoppingCarts();
    }

    protected void tearDownEnvironment() {
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        personRepository.deleteAll();
    }

    private void setupCustomers() {
        person1 = createCustomer("alice", "Alice", "Wonderland", "alice@wonderland.org", LocalDate.of(1980, 1, 12), Person.Role.CUSTOMER);
        person2 = createCustomer("bob", "Bob", "Marley", "bob@marley.org", LocalDate.of(1970, 2, 23), Person.Role.CUSTOMER);
        person3 = createCustomer("charlie", "Charlie", "Brown", "charlie@brown.org", LocalDate.of(1960, 3, 30), Person.Role.CUSTOMER);
        person4 = createCustomer("dave", "Dave", "Smith", "dave@smith.org", LocalDate.of(1950, 4, 14), Person.Role.CUSTOMER);
        person5 = createCustomer("eve", "Eve", "Jackson", "eve@jackson.org", LocalDate.of(1940, 5, 15), Person.Role.CUSTOMER);
    }

    private Person createCustomer(String username, String firstName, String lastName, String email, LocalDate birthDate, Person.Role role) {
        return personRepository.findByUsername(username).orElseGet(
                () -> {
                    Person person = new Person();
                    person.setUsername(username);
                    person.setFirstName(firstName);
                    person.setLastName(lastName);
                    person.setEmail(email);
                    person.setBirthdate(birthDate);
                    person.setRole(role);
                    return personRepository.save(person);
                }
        );
    }

    private void setupProducts() {
        product1 = createProduct("123456", "Product 1", "Description 1", 12.34);
        product2 = createProduct("123457", "Product 2", "Description 2", 23.45);
        product3 = createProduct("123458", "Product 3", "Description 3", 34.56);
        product4 = createProduct("123459", "Product 4", "Descript1on 4", 45.67);
        product5 = createProduct("123460", "Product 5", "Description 5", 56.78);
        product6 = createProduct("123461", "Product 6", "Descript1on_6", 67.89);
        product7 = createProduct("123462", "Product 7", "Descript1on_7", 78.90);
        product8 = createProduct("123463", "Product 8", "Descript1on 8", 89.01);
        product9 = createProduct("123464", "Product 9", "Description 9", 90.12);
        product10 = createProduct("123465", "Product 10", "Description 10", 122.23);
        product11 = createProduct("123466", "Product 11", "Description 11", 222.34);
        product12 = createProduct("123467", "Product 12", "Description 12", 334.45);
        product13 = createProduct("123468", "Product 13", "Description 13", 499.56);
    }

    @Autowired
    private ProductRepository productRepository;

    private Product createProduct(String number, String name, String description, double price) {
        return productRepository.findByNumber(number).orElseGet(
                () -> {
                    Product product = new Product();
                    product.setNumber(number);
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    return productRepository.save(product);
                }
        );
    }

    private void setupShoppingCarts() {
        shoppingCart1 = createShoppingCart(person1, Map.of(product1, 1L, product2, 2L, product3, 3L)); // 1 * 12.34 + 2 * 23.45 + 3 * 34.56 = 162.92
        shoppingCart2 = createShoppingCart(person2, Map.of(product4, 4L, product5, 5L, product6, 6L)); // 4 * 45.67 + 5 * 56.78 + 6 * 67.89 = 873.92
        shoppingCart3 = createShoppingCart(person3, Map.of(product7, 7L, product8, 8L, product9, 9L)); // 7 * 78.90 + 8 * 89.01 + 9 * 90.12 = 2075.46
        shoppingCart4 = createShoppingCart(person4, Map.of(product10, 10L, product11, 11L, product12, 12L)); // 10 x 122.23 + 11 x 222.34 + 12 x 334.45 = 7681.44
        shoppingCart5 = createShoppingCart(person5, Map.of(product1, 1L, product2, 2L, product3, 3L)); // 1 * 12.34 + 2 * 23.45 + 3 * 34.56 = 162.92
    }

    private ShoppingCart createShoppingCart(Person person, Map<Product, Long> productsWithQuantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(person);
        shoppingCart.setCheckedOut(false);

        productsWithQuantity.forEach(shoppingCart::addItem);

        return shoppingCartRepository.findByCustomerId(person.getId()).orElseGet(
                () -> shoppingCartRepository.save(shoppingCart));
    }

    private void setupRestAssured() {
        RestAssured.port = port;
        RestAssured.defaultParser = JSON;
    }
}
