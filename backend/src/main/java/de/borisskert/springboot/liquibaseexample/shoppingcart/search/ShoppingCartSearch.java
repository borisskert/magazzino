package de.borisskert.springboot.liquibaseexample.shoppingcart.search;

public record ShoppingCartSearch(Long id, String productNumber, String productName, Double minTotalPrice) {
}
