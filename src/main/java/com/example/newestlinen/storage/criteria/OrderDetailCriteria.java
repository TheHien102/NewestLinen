package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Account;
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
    private int status = -99;

    public Specification<OrderDetail> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getStatus() != -99) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<OrderDetail> getSpecification(Long accountId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountId != null) {
                Join<OrderDetail, Order> joinOrder = root.join("order", JoinType.INNER);
                Join<Order, Account> joinAccount = joinOrder.join("account", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinAccount.get("id"), accountId));
            }
            if (getStatus() != -999) {
                predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
