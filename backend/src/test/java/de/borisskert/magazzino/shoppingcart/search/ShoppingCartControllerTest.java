package de.borisskert.magazzino.shoppingcart.search;

import de.borisskert.magazzino.TestSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ShoppingCartControllerTest extends TestSetup {

    private static final String SHOPPING_CART_SEARCH_PATH = "/api/shopping-cart/search";

    @BeforeEach
    void setup() throws Exception {
        super.setupEnvironment();
    }

    @Test
    void shouldFindAllShoppingCarts() throws Exception {
        given()
                .when()
                .param("sort", "id,asc")
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
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
                .get(SHOPPING_CART_SEARCH_PATH)
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(2),
                        "content[0].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart4.getId().intValue())
                );
    }

    @Test
    void shouldFindCartsSortedByTotalPrice() throws Exception {
        given()
                .when()
                .param("sort", "totalPrice,desc")
                .get(SHOPPING_CART_SEARCH_PATH)
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(5),
                        "content[0].id", equalTo(shoppingCart4.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[2].id", equalTo(shoppingCart2.getId().intValue()),
                        "content[3].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[4].id", equalTo(shoppingCart5.getId().intValue())
                );
    }

    @Test
    void shouldFindCartsSortedByArticleCount() throws Exception {
        given()
                .when()
                .param("sort", "articleCount,asc")
                .get(SHOPPING_CART_SEARCH_PATH)
                .then()
                .statusCode(200)
                .body(
                        "content.size()", equalTo(5),
                        "content[0].id", equalTo(shoppingCart1.getId().intValue()),
                        "content[1].id", equalTo(shoppingCart5.getId().intValue()),
                        "content[2].id", equalTo(shoppingCart2.getId().intValue()),
                        "content[3].id", equalTo(shoppingCart3.getId().intValue()),
                        "content[4].id", equalTo(shoppingCart4.getId().intValue())
                );
    }

    @AfterEach
    void tearDown() {
        super.tearDownEnvironment();
    }
}
