package de.borisskert.magazzino.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> search(ProductSearchRequest search, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.from(search);

        try {
            return productRepository.findAll(specification, pageable);
        } catch (PropertyReferenceException e) {
            throw new ProductSearchException(e);
        }
    }

    @Transactional
    public void create(CreateProductRequest productToCreate) {
        if (productRepository.existsByName(productToCreate.name())) {
            throw new ProductAlreadyExistsException(
                    "Product with name '" + productToCreate.name() + "' already exists"
            );
        }

        Product entity = productToCreate.toEntity();
        productRepository.save(entity);
    }

    public void update(UUID productId, UpdateProductRequest productToUpdate) {
        Product entity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));

        productToUpdate.update(entity);
        productRepository.save(entity);
    }
}
