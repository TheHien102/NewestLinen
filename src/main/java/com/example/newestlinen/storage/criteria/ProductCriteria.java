package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Category;
import com.example.newestlinen.storage.model.ProductModel.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCriteria {
    private Long id;

    private String name;

    private int discount;

    private String description;

    private int price;

    private Long productCategoryId;

    public Specification<Product> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
            }
            if (getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }
            if (getDiscount() != 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discount"), getDiscount()));
            }
            if (getPrice() != 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), getPrice()));
            }
            if (getProductCategoryId() != null) {
                Join<Product, Category> joinCategory = root.join("productCategory", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinCategory.get("id"), getProductCategoryId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
