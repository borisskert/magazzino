package de.borisskert.springboot.liquibaseexample;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ShoppingCartSearchResult {

    public Long id;
    public Customer customer;
    public List<Item> items;
    public Boolean checkedOut;

    public static class Customer {
        public Long id;
        public String firstName;
        public String lastName;

        public static Customer fromEntity(Person customer) {
            Customer result = new Customer();
            result.id = customer.getId();
            result.firstName = customer.getFirstName();
            result.lastName = customer.getLastName();

            return result;
        }
    }

    public static class ProductDto {
        public UUID id;
        public String name;
        public String number;
        public Double price;

        public static ProductDto fromEntity(Product product) {
            ProductDto result = new ProductDto();
            result.id = product.getId();
            result.name = product.getName();
            result.number = product.getNumber();
            result.price = product.getPrice();

            return result;
        }
    }

    public static class Item {
        public ProductDto product;
        public Long quantity;

        public static Item fromEntity(ShoppingCartItem item) {
            Item result = new Item();

            result.quantity = item.getQuantity();
            result.product = ProductDto.fromEntity(item.getProduct());

            return result;
        }
    }

    public static ShoppingCartSearchResult fromEntity(ShoppingCart cart) {
        ShoppingCartSearchResult shoppingCartSearchResult = new ShoppingCartSearchResult();
        shoppingCartSearchResult.id = cart.getId();

        shoppingCartSearchResult.customer = ShoppingCartSearchResult.Customer.fromEntity(cart.getCustomer());
        shoppingCartSearchResult.items = cart.getItems()
                .stream()
                .map(Item::fromEntity)
                .sorted(Comparator.comparing(a -> a.product.name))
                .toList();

        shoppingCartSearchResult.checkedOut = cart.isCheckedOut();

        return shoppingCartSearchResult;
    }
}
