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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Person customer;

    @Column(nullable = false)
    private Boolean checkedOut;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private List<ShoppingCartItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
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
