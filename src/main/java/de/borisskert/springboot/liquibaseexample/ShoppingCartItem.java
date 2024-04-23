package de.borisskert.springboot.liquibaseexample;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shopping_cart_id", nullable = false)
    private Long shoppingCartId;

    @Column(name = "product_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID productId;

    private Long quantity;

    public Long getId() {
        return id;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
