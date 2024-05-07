package de.borisskert.springboot.liquibaseexample;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    
    Optional<ShoppingCart> findByCustomerId(Long customerId);
}
