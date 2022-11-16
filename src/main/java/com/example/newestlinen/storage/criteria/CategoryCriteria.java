package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Category;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryCriteria {
    private Long id;
    private Integer kind;
    private String name;
    private Long parentId;
    private Integer status;

    public Specification<Category> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
//                Join<Category, Category> joinCategory = root.join("parentCategory", JoinType.INNER);

                if(getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }

                if(!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }

                if(getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getParentId() != null) {
                    predicates.add(cb.equal(root.get("parentCategory"), getParentId()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
