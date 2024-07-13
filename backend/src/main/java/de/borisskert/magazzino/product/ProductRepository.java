package de.borisskert.magazzino.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    Optional<Product> findByNumber(String number);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}
