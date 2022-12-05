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
@Table(name = TablePrefix.PREFIX_TABLE+"AddressManagement")
public class AddressManagement extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AddressManagement_ID")
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nullable
    @JoinColumn(name = "Parent_ID", referencedColumnName = "AddressManagement_ID")
    @JsonBackReference
    @JsonIgnore
    private AddressManagement parent;
}
