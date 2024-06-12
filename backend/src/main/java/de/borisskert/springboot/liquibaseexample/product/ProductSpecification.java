package de.borisskert.springboot.liquibaseexample.product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification implements Specification<Product> {

    private final String name;

    private ProductSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return name == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                // In PostgreSQL, the LIKE operator is case-sensitive by default
                // https://stackoverflow.com/a/16783603/13213024
                criteriaBuilder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%"
        );
    }

    public static Specification<Product> from(ProductSearchDto search) {
        return new ProductSpecification(search.name());
    }
}
