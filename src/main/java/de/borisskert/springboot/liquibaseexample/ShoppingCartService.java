package de.borisskert.springboot.liquibaseexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(
            ShoppingCartRepository shoppingCartRepository,
            ProductRepository productRepository
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ShoppingCart retrieveShoppingCart(Long customerId) {
        return shoppingCartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setCustomerId(customerId);
                    shoppingCart.setCheckedOut(false);
                    return shoppingCartRepository.save(shoppingCart);
                });
    }

    @Transactional
    public void addItemToCart(Long customerId, UUID productId, Long quantity) {
        ShoppingCart shoppingCart = retrieveShoppingCart(customerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        shoppingCart.addItem(product, quantity);
        shoppingCartRepository.save(shoppingCart);
    }
}
