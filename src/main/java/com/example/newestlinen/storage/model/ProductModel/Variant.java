package com.example.newestlinen.storage.model.ProductModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Variant")
@Getter
@Setter
@ToString
@Embeddable
public class Variant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Variant_ID")
    private Long id;

    private String name;

    private String property;

    private int addPrice;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variant) {
            Variant a = (Variant) obj;
            return Objects.equals(a.getId(), this.id);
        }
        return false;
    }

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Product_ID")
    private Product variantProduct;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Item_Variant",
            joinColumns = @JoinColumn(name = "Variant_ID"),
            inverseJoinColumns = @JoinColumn(name = "Item_ID"))
    private List<Item> variantItem = new ArrayList<>();
}
