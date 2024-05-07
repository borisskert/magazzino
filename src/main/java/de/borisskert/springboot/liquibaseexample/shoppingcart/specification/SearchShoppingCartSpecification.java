package de.borisskert.springboot.liquibaseexample.shoppingcart.specification;

import de.borisskert.springboot.liquibaseexample.product.Product;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCart;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCartItem;
import de.borisskert.springboot.liquibaseexample.shoppingcart.search.ShoppingCartSearch;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import static de.borisskert.springboot.liquibaseexample.shoppingcart.specification.TotalPriceShoppingCartSpecification.searchMinTotalPrice;

public class SearchShoppingCartSpecification implements Specification<ShoppingCart> {
    private final Long id;
    private final String productNumber;
    private final String productName;
    private final Double minTotalPrice;

    private SearchShoppingCartSpecification(Long id, String productNumber, String productName, Double minTotalPrice) {
        this.id = id;
        this.productNumber = productNumber;
        this.productName = productName;
        this.minTotalPrice = minTotalPrice;
    }

    @Override
    public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<ShoppingCartItem, ShoppingCart> items = root.join("items", JoinType.LEFT);
        Join<Product, ShoppingCart> products = items.join("product", JoinType.LEFT);

        return criteriaBuilder.and(
                id == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("id"), id),
                productNumber == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(products.get("number"), productNumber),
                productName == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        // In PostgreSQL, the LIKE operator is case-sensitive by default
                        // https://stackoverflow.com/a/16783603/13213024
                        criteriaBuilder.lower(products.get("name")),
                        "%" + productName.toLowerCase() + "%"
                )
                , minTotalPrice == null ? criteriaBuilder.conjunction() : searchMinTotalPrice(minTotalPrice)
                        .toPredicate(root, query, criteriaBuilder)
        );
    }

    public static Specification<ShoppingCart> from(ShoppingCartSearch search) {
        return new SearchShoppingCartSpecification(
                search.id(),
                search.productNumber(),
                search.productName(),
                search.minTotalPrice()
        );
    }
}
