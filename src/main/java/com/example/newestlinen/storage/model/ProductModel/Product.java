package com.example.newestlinen.storage.model.ProductModel;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.Category;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Product")
@Getter
@Setter
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    @JsonIgnore
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private String description;

    private int price;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category productCategory;

    @OneToMany(mappedBy = "assetProduct", cascade = CascadeType.ALL)
    private List<Asset> assets;

    @OneToMany(mappedBy = "reviewProduct", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "itemProduct", cascade = CascadeType.ALL)
    private List<Item> productItem;
}
