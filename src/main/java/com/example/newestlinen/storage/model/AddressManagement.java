package com.example.newestlinen.storage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @Nullable
    @JoinColumn(name = "Parent_ID", referencedColumnName = "AddressManagement_ID")
    @JsonBackReference
    @JsonIgnore
    private AddressManagement parent;
}
