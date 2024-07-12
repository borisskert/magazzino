package de.borisskert.springboot.liquibaseexample.product;

public record CreateProductRequest(
        String number,
        String name,
        String description,
        Double price
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
