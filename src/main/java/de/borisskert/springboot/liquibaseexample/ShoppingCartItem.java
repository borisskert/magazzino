package de.borisskert.springboot.liquibaseexample;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "shopping_cart_id", nullable = false)
//    private Long shoppingCartId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    private ShoppingCart shoppingCart;

    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ShoppingCartItem withQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    public ShoppingCartItem withCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }
}
