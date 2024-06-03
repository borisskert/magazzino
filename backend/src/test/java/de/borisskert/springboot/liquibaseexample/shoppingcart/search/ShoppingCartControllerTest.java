package de.borisskert.springboot.liquibaseexample.shoppingcart.search;

import de.borisskert.springboot.liquibaseexample.person.Person;
import de.borisskert.springboot.liquibaseexample.person.PersonRepository;
import de.borisskert.springboot.liquibaseexample.product.Product;
import de.borisskert.springboot.liquibaseexample.product.ProductRepository;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCart;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCartRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.parsing.Parser.JSON;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ShoppingCartControllerTest {

    @BeforeEach
    void setup() throws Exception {
        setupRestAssured();
        setupCustomers();
        setupProducts();
        setupShoppingCarts();
    }

    @Test
    void shouldFindAllShoppingCarts() throws Exception {
        given()
                .when()
                .param("sort", "id,asc")
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(5),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart2.getId().intValue()),
                        "content[2].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[3].id", equalTo(shoppingCart4.getId().intValue()),
                        "content[4].id", equalTo(shoppingCart5.getId().intValue())
                );
    }

    @Test
    void shouldFindShoppingCartsForFirstPageAndSizeTwo() throws Exception {
        given()
                .when()
                .param("sort", "id,asc")
                .param("page", 0)
                .param("size", 2)
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "size", equalTo(2),
                        "number", equalTo(0),
                        "totalPages", equalTo(3),
                        "first", equalTo(true),
                        "last", equalTo(false),
                        "content.size()", equalTo(2),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart2.getId().intValue())
                );
    }

    @Test
    void shouldFindShoppingCartsForSecondPageAndSizeTwo() throws Exception {
        given()
                .when()
                .param("sort", "id,asc")
                .param("page", 1)
                .param("size", 2)
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "size", equalTo(2),
                        "number", equalTo(1),
                        "totalPages", equalTo(3),
                        "first", equalTo(false),
                        "last", equalTo(false),
                        "content.size()", equalTo(2),
                        "content[0].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart4.getId().intValue())
                );
    }

    @Test
    void shouldFindShoppingCartsForLastPageAndSizeTwo() throws Exception {
        given()
                .when()
                .param("sort", "id,asc")
                .param("page", 2)
                .param("size", 2)
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "size", equalTo(2),
                        "number", equalTo(2),
                        "totalPages", equalTo(3),
                        "first", equalTo(false),
                        "last", equalTo(true),
                        "content.size()", equalTo(1),
                        "content[0].id", equalTo(shoppingCart5.getId().intValue())
                );
    }

    @Test
    void shouldGetPropertiesInResult() throws Exception {
        given()
                .when()
                .param("id", shoppingCart1.getId())
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(1),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[0].checkedOut", equalTo(shoppingCart1.isCheckedOut()),

                        "content[0].customer.id", equalTo(shoppingCart1.getCustomer().getId().intValue()),
                        "content[0].customer.firstName", equalTo(shoppingCart1.getCustomer().getFirstName()),
                        "content[0].customer.lastName", equalTo(shoppingCart1.getCustomer().getLastName()),

                        "content[0].items.size()", equalTo(3),

                        "content[0].items[0].product.id", equalTo(product1.getId().toString()),
                        "content[0].items[0].product.number", equalTo(product1.getNumber()),
                        "content[0].items[0].product.name", equalTo(product1.getName()),
                        "content[0].items[0].product.price", equalTo(product1.getPrice().floatValue()),
                        "content[0].items[0].quantity", equalTo(1),

                        "content[0].items[1].product.id", equalTo(product2.getId().toString()),
                        "content[0].items[1].product.number", equalTo(product2.getNumber()),
                        "content[0].items[1].product.name", equalTo(product2.getName()),
                        "content[0].items[1].product.price", equalTo(product2.getPrice().floatValue()),
                        "content[0].items[1].quantity", equalTo(2),

                        "content[0].items[2].product.id", equalTo(product3.getId().toString()),
                        "content[0].items[2].product.number", equalTo(product3.getNumber()),
                        "content[0].items[2].product.name", equalTo(product3.getName()),
                        "content[0].items[2].product.price", equalTo(product3.getPrice().floatValue()),
                        "content[0].items[2].quantity", equalTo(3),

                        "content[0].totalPrice", equalTo(162.92f)
                );
    }

    @Test
    void shouldFindCartsWithContainingProductByNumber() throws Exception {
        given()
                .when()
                .param("productNumber", product1.getNumber())
                .param("sort", "id,asc")
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(2),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart5.getId().intValue())
                );
    }

    @Test
    void shouldFindCartsWithContainingProductByName() throws Exception {
        given()
                .when()
                .param("productName", "uCt 1")
                .param("sort", "id,asc")
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(3),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart4.getId().intValue()),
                        "content[2].id", equalTo(shoppingCart5.getId().intValue())
                );
    }

    @Test
    void shouldFindCartsWithContainingProductsWorthMoreThan() throws Exception {
        given()
                .when()
                .param("minTotalPrice", 2000.0)
                .param("sort", "id,asc")
                .get("/shopping-cart/search")
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(2),
                        "content[0].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart4.getId().intValue())
                );
    }

    @Autowired
    private PersonRepository personRepository;

    private Person person1, person2, person3, person4, person5;

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

    private Product product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11, product12, product13;

    private void setupProducts() {
        product1 = createProduct("123456", "Product 1", "Description 1", 12.34);
        product2 = createProduct("123457", "Product 2", "Description 2", 23.45);
        product3 = createProduct("123458", "Product 3", "Description 3", 34.56);
        product4 = createProduct("123459", "Product 4", "Description 4", 45.67);
        product5 = createProduct("123460", "Product 5", "Description 5", 56.78);
        product6 = createProduct("123461", "Product 6", "Description 6", 67.89);
        product7 = createProduct("123462", "Product 7", "Description 7", 78.90);
        product8 = createProduct("123463", "Product 8", "Description 8", 89.01);
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

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCart shoppingCart1, shoppingCart2, shoppingCart3, shoppingCart4, shoppingCart5;

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

    @Value("${local.server.port}")
    private int port;

    private void setupRestAssured() {
        RestAssured.port = port;
        RestAssured.defaultParser = JSON;
    }
}
