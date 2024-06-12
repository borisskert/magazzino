package de.borisskert.springboot.liquibaseexample.product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification implements Specification<Product> {

    private final String name;
    private final String number;

    private ProductSpecification(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                searchByName(root, criteriaBuilder),
                searchByNumber(root, criteriaBuilder)
        );
    }

    private Predicate searchByName(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return name == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                // In PostgreSQL and H2, the LIKE operator is case-sensitive by default
                // https://stackoverflow.com/a/16783603/13213024
                criteriaBuilder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%"
        );
    }

    private Predicate searchByNumber(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return number == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("number"), number);
    }

    public static Specification<Product> from(ProductSearchDto search) {
        return new ProductSpecification(search.name(), search.number());
    }
}
