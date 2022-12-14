package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Category;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCriteria {
    private String name;

    private int discount;

    private String description;

    private int price;

    private Long productCategoryId;

    private String productCategoryName;

    private String variant;

    public Specification<Product> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + getName().toLowerCase() + "%"));
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
            if (getProductCategoryName() != null) {
                Join<Product, Category> joinCategory = root.join("productCategory", JoinType.INNER);
                predicates.add(criteriaBuilder.like(joinCategory.get("name"), "%" + getProductCategoryName().toLowerCase() + "%"));
            }
            if(getVariant()!=null){
                Join<Product, Variant> joinVariant = root.join("variants", JoinType.INNER);
                predicates.add(criteriaBuilder.like(joinVariant.get("property"), "%" + getVariant().toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
