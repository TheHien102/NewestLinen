package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.storage.model.CartModel.Cart;
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

    private Long accountId = null;

    private int status = -999;

    public Specification<CartItem> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getAccountId() != null) {
                Join<CartItem, Cart> joinCart = root.join("cart", JoinType.INNER);
                Join<Cart, Account> joinAccount = joinCart.join("account", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinAccount.get("id"), getAccountId()));
            }
            if (getStatus() != -999) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
