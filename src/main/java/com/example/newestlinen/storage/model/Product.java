package com.example.newestlinen.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    private Long id;

    private String name;

    private int discount;

    private String description;

    private int price;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category productCategory;

    @OneToMany(mappedBy = "assetProduct")
    private List<Asset> Assets;

    @OneToMany(mappedBy = "reviewProduct")
    private List<Review> Reviews;

    @OneToMany(mappedBy = "itemProduct")
    private List<Item> productItem;
}
