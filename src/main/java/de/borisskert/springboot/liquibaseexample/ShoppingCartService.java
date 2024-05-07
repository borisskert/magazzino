package de.borisskert.springboot.liquibaseexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static de.borisskert.springboot.liquibaseexample.ShoppingCartSpecification.from;

@Service
public class ShoppingCartService {

    private final PersonRepository personRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(
            PersonRepository personRepository,
            ShoppingCartRepository shoppingCartRepository,
            ProductRepository productRepository
    ) {
        this.personRepository = personRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ShoppingCart retrieveShoppingCart(Long customerId) {
        Person person = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found: " + customerId));

        return shoppingCartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setCustomer(person);
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

    public Page<ShoppingCart> search(ShoppingCartSearch search, Pageable pageable) {
        return shoppingCartRepository.findAll(from(search), pageable);
    }
}
