package de.borisskert.springboot.liquibaseexample.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
        @NotNull String number,
        @NotNull String name,
        @NotNull String description,
        @NotNull @Min(0) Double price
) {
    public Product toEntity() {
        Product entity = new Product();
        entity.setNumber(number());
        entity.setName(name());
        entity.setDescription(description());
        entity.setPrice(price());

        return entity;
    }
}
