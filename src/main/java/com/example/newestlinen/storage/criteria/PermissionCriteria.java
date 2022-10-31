package com.example.newestlinen.storage.criteria;

import com.example.newestlinen.storage.model.Permission;
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
public class PermissionCriteria {
    private Long id;
    private String name;
    private String action;
    private Boolean showMenu;
    private String description;
    private String nameGroup;

    public Specification<Permission> getSpecification() {
        return new Specification<Permission>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if(getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")), "%"+getName().toLowerCase()+"%"));
                }
                if(!StringUtils.isEmpty(getDescription())){
                    predicates.add(cb.like(cb.lower(root.get("description")), "%"+getDescription().toLowerCase()+"%"));
                }
                if(!StringUtils.isEmpty(getAction())){
                    predicates.add(cb.like(cb.lower(root.get("action")), "%"+getAction().toLowerCase()+"%"));
                }
                if(!StringUtils.isEmpty(getNameGroup())){
                    predicates.add(cb.like(cb.lower(root.get("nameGroup")), "%"+getNameGroup().toLowerCase()+"%"));
                }
                if(getShowMenu() != null) {
                    predicates.add(cb.equal(root.get("showMenu"), getShowMenu()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
