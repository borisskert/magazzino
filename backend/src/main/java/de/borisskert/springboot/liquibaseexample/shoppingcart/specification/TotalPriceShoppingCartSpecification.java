package de.borisskert.springboot.liquibaseexample.shoppingcart.specification;

import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCart;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TotalPriceShoppingCartSpecification implements Specification<ShoppingCart> {
    private final Double minTotalPrice;

    private TotalPriceShoppingCartSpecification(Double minTotalPrice) {
        this.minTotalPrice = minTotalPrice;
    }

    @Override
    public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var selection = query.subquery(Long.class);
        var subRoot = selection.from(ShoppingCart.class);

        var items = subRoot.join("items");
        var product = items.join("product");

        selection.select(subRoot.get("id"))
                .groupBy(subRoot.get("id"))
                .having(
                        criteriaBuilder.greaterThan(
                                criteriaBuilder.sum(
                                        criteriaBuilder.prod(
                                                items.get("quantity"),
                                                product.get("price")
                                        )
                                ),
                                minTotalPrice
                        )
                );

        return criteriaBuilder.in(root.get("id")).value(selection);
    }

    public static Specification<ShoppingCart> searchMinTotalPrice(Double minTotalPrice) {
        return new TotalPriceShoppingCartSpecification(minTotalPrice);
    }
}
