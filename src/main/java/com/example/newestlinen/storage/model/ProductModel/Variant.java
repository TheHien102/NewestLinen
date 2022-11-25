package com.example.newestlinen.storage.model.ProductModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.Category;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Variant")
@Getter
@Setter
@ToString
public class Variant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Varriant_ID")
    private Long id;

    private String name;

    private String property;

    private int addPrice;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Product_ID")
    private Product variantProduct;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Item_Variant",
            joinColumns = @JoinColumn(name = "Variant_ID"),
            inverseJoinColumns = @JoinColumn(name = "Item_ID"))
    private List<Item> variantItem;
}
