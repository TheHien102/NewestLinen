package com.example.newestlinen.storage.model.CartModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Item")
@Getter
@Setter
public class Item extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Item_ID")
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Product_ID", referencedColumnName = "Product_ID")
    private Product itemProduct;

    @ManyToMany(mappedBy = "variantItem", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Variant> Variants = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CartItem cartItem;

    @OneToOne(cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderDetail orderDetail;
}
