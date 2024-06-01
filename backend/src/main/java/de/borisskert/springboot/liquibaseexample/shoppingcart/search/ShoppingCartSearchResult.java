package de.borisskert.springboot.liquibaseexample.shoppingcart.search;

import de.borisskert.springboot.liquibaseexample.person.Person;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCart;
import de.borisskert.springboot.liquibaseexample.shoppingcart.persistence.ShoppingCartItem;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record ShoppingCartSearchResult(
        Long id,
        Customer customer,
        List<Item> items, Boolean checkedOut
) {

    public record Customer(Long id, String firstName, String lastName) {

        public static Customer fromEntity(Person customer) {
            return new Customer(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName()
            );
        }
    }

    public record Product(UUID id, String name, String number, Double price) {
        
        public static Product fromEntity(de.borisskert.springboot.liquibaseexample.product.Product product) {
            return new Product(
                    product.getId(),
                    product.getName(),
                    product.getNumber(),
                    product.getPrice()
            );
        }
    }

    public record Item(Product product, Long quantity) {
        
        public static Item fromEntity(ShoppingCartItem item) {
            return new Item(
                    Product.fromEntity(item.getProduct()),
                    item.getQuantity()
            );
        }
    }

    public static ShoppingCartSearchResult fromEntity(ShoppingCart cart) {
        List<Item> items = cart.getItems()
                .stream()
                .map(Item::fromEntity)
                .sorted(Comparator.comparing(item -> item.product.name))
                .toList();

        return new ShoppingCartSearchResult(
                cart.getId(),
                Customer.fromEntity(cart.getCustomer()),
                items,
                cart.isCheckedOut()
        );
    }
}
