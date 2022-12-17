package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartItemCriteria {

    private int status = -999;

    public Specification<CartItem> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getStatus() != -999) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<CartItem> getSpecification(Long accountId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountId != null) {
                Join<CartItem, Account> joinAccount = root.join("account", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinAccount.get("id"), accountId));
            }
            if (getStatus() != -999) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
