package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.storage.model.ProductModel.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProvinceCriteria {
    private String name;

    private int level = -1;

    public Specification<Province> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + getName().toLowerCase() + "%"));
            }
            if (getLevel() != -1) {
                predicates.add(criteriaBuilder.equal(root.get("level"),getLevel()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
