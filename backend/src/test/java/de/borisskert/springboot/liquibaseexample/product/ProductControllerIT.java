package de.borisskert.springboot.liquibaseexample.product;

import de.borisskert.springboot.liquibaseexample.TestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static de.borisskert.springboot.liquibaseexample.Utils.toList;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductControllerIT extends TestSetup {
    private static final String PRODUCTS_SEARCH_PATH = "/api/products/search";

    @BeforeEach
    void setup() throws Exception {
        super.setupEnvironment();
    }

    @Nested
    class SearchProduct {

        @Test
        void shouldFindAll() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(EXPECTED_NUMBER_OF_PRODUCTS),
                            "content[0].id", equalTo(product1.getId().toString()),
                            "content[1].id", equalTo(product2.getId().toString()),
                            "content[2].id", equalTo(product3.getId().toString()),
                            "content[3].id", equalTo(product4.getId().toString()),
                            "content[4].id", equalTo(product5.getId().toString()),
                            "content[5].id", equalTo(product6.getId().toString()),
                            "content[6].id", equalTo(product7.getId().toString()),
                            "content[7].id", equalTo(product8.getId().toString()),
                            "content[8].id", equalTo(product9.getId().toString()),
                            "content[9].id", equalTo(product10.getId().toString()),
                            "content[10].id", equalTo(product11.getId().toString()),
                            "content[11].id", equalTo(product12.getId().toString()),
                            "content[12].id", equalTo(product13.getId().toString())
                    );
        }

        @Test
        void shouldFindFirstPageOfSize3() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .param("page", 0)
                    .param("size", 3)
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(3),
                            "content[0].id", equalTo(product1.getId().toString()),
                            "content[1].id", equalTo(product2.getId().toString()),
                            "content[2].id", equalTo(product3.getId().toString()),
                            "size", equalTo(3),
                            "number", equalTo(0),
                            "totalPages", equalTo(5),
                            "totalElements", equalTo(EXPECTED_NUMBER_OF_PRODUCTS),
                            "first", equalTo(true),
                            "last", equalTo(false)
                    );
        }

        @Test
        void shouldFindLastPageOfSize4() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .param("page", 3)
                    .param("size", 4)
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(1),
                            "content[0].id", equalTo(product13.getId().toString()),
                            "size", equalTo(4),
                            "number", equalTo(3),
                            "totalPages", equalTo(4),
                            "totalElements", equalTo(EXPECTED_NUMBER_OF_PRODUCTS),
                            "first", equalTo(false),
                            "last", equalTo(true)
                    );
        }

        @Test
        void shouldFilterByName() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .param("name", "duCT 1")
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(5),
                            "content[0].id", equalTo(product1.getId().toString()),
                            "content[1].id", equalTo(product10.getId().toString()),
                            "content[2].id", equalTo(product11.getId().toString()),
                            "content[3].id", equalTo(product12.getId().toString()),
                            "content[4].id", equalTo(product13.getId().toString())
                    );
        }

        @Test
        void shouldFilterByNumber() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .param("number", "123463")
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(1),
                            "content[0].id", equalTo(product8.getId().toString())
                    );
        }

        @Test
        void shouldFilterByDescription() throws Exception {
            given()
                    .when()
                    .param("sort", "number,asc")
                    .param("description", "rIPT1on_")
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(200)
                    .body(
                            "content.size()", equalTo(4),
                            "content[0].id", equalTo(product4.getId().toString()),
                            "content[1].id", equalTo(product6.getId().toString()),
                            "content[2].id", equalTo(product7.getId().toString()),
                            "content[3].id", equalTo(product8.getId().toString())
                    );
        }

        @Test
        void shouldFailWhenSortIsInvalid() throws Exception {
            given()
                    .when()
                    .param("sort", "[object Object]")
                    .param("page", 0)
                    .param("size", 3)
                    .get(PRODUCTS_SEARCH_PATH)
                    .then()
                    .statusCode(400);
        }
    }

    @Nested
    class CreateProduct {
        @Test
        void shouldCreateProduct() throws Exception {
            var payload = new JSONObject()
                    .put("number", "8482392")
                    .put("name", "Product 3472312")
                    .put("description", "Description 123456")
                    .put("price", 123.45);

            given()
                    .contentType("application/json")
                    .body(payload.toString())
                    .when()
                    .post("/api/products")
                    .then()
                    .statusCode(201);

            List<Product> foundProducts = toList(productRepository.findAll());

            assertThat(foundProducts).hasSize(EXPECTED_NUMBER_OF_PRODUCTS + 1);

            Product createdProduct = foundProducts.get(EXPECTED_NUMBER_OF_PRODUCTS);
            assertThat(createdProduct.getId()).isNotNull();
            assertThat(createdProduct.getNumber()).isEqualTo("8482392");
            assertThat(createdProduct.getName()).isEqualTo("Product 3472312");
            assertThat(createdProduct.getDescription()).isEqualTo("Description 123456");
            assertThat(createdProduct.getPrice()).isEqualTo(123.45);
        }
    }

    @AfterEach
    void tearDown() {
        super.tearDownEnvironment();
    }
}
