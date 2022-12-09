package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemCriteria {

    private Long cartId;

    private int status;

    public Specification<CartItem> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getCartId() != null) {
                Join<Province, Province> joinCategory = root.join("cart", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinCategory.get("id"),getCartId()));
            }
            if (getStatus() != -1) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
