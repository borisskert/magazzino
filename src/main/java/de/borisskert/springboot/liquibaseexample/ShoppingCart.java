package de.borisskert.springboot.liquibaseexample;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Boolean checkedOut;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private List<ShoppingCartItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void addItem(Product product, Long quantity) {
        ShoppingCartItem existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> {
                    ShoppingCartItem newItem = new ShoppingCartItem();
                    newItem.setShoppingCart(this);
                    newItem.setProduct(product);
                    newItem.setQuantity(0L);
                    items.add(newItem);
                    return newItem;
                });

        existingItem.setQuantity(existingItem.getQuantity() + quantity);
    }
}
