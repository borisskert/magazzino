package de.borisskert.springboot.liquibaseexample.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> search(ProductSearchDto search, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.from(search);

        try {
            return productRepository.findAll(specification, pageable);
        } catch (PropertyReferenceException e) {
            throw new ProductSearchException(e);
        }
    }

    public void create(CreateProductRequest productToCreate) {
        Product entity = productToCreate.toEntity();
        productRepository.save(entity);
    }
}
