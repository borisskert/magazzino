package de.borisskert.magazzino.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(
        @NotNull String number,
        @NotNull String name,
        @NotNull String description,
        @NotNull @Min(0) Double price
) {
    public void update(Product entity) {
        entity.setNumber(number);
        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
    }
}
