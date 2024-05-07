package de.borisskert.springboot.liquibaseexample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    public List<ShoppingCartItem> findByShoppingCartId(Long shoppingCartId);
}
