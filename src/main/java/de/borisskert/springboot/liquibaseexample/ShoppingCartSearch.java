package de.borisskert.springboot.liquibaseexample;

public record ShoppingCartSearch(Long id, String productNumber, String productName, Double minTotalPrice) {
}
