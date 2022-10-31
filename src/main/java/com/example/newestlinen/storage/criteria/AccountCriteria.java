package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Group;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountCriteria  {
    private Long id;
    private Integer kind;
    private String username;
    private String email;
    private String fullName;
    private Long groupId;
    private String phone;
    private Integer status;

    public Specification<Account> getSpecification() {
        return new Specification<Account>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if(getKind() != null){
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if(getGroupId() != null){
                    Join<Account, Group> joinGroup = root.join("group", JoinType.INNER);
                    predicates.add(cb.equal(joinGroup.get("id"), getGroupId()));
                }
                if(!StringUtils.isEmpty(getUsername())){
                    predicates.add(cb.like(cb.lower(root.get("username")), "%"+getUsername().toLowerCase()+"%"));
                }
                if(!StringUtils.isEmpty(getEmail())){
                    predicates.add(cb.like(cb.lower(root.get("email")), "%"+getEmail().toLowerCase()+"%"));
                }
                if(!StringUtils.isEmpty(getFullName())){
                    predicates.add(cb.like(cb.lower(root.get("fullName")), "%"+getFullName().toLowerCase()+"%"));
                }

                if(!StringUtils.isEmpty(getPhone())){
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%"+getPhone().toLowerCase()+"%"));
                }
                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
