package com.example.newestlinen.storage.model.Address;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE+"Province")
public class Province extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Province_ID")
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nullable
    @JoinColumn(name = "Parent_ID", referencedColumnName = "Province_ID")
    @JsonBackReference
    @JsonIgnore
    private Province parent;
}
