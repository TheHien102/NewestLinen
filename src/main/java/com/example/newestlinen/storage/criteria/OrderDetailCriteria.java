package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.CartModel.Cart;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.OrderModel.Order;
import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailCriteria {
    private Long accountId = null;
    private String phoneNumber = null;
    private int status = -999;

    public Specification<OrderDetail> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getAccountId() != null) {
                Join<OrderDetail, Order> joinOrder = root.join("order", JoinType.INNER);
                Join<Order, Account> joinAccount = joinOrder.join("account", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinAccount.get("id"), getAccountId()));
            }
            if (getPhoneNumber() != null) {
                Join<OrderDetail, Order> joinOrder = root.join("order", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinOrder.get("phoneNumber"), getPhoneNumber()));
            }
            if (getStatus() != -999) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
