package com.example.newestlinen.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.newestlinen.storage.model.ProductModel.Product;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Asset")
@Setter
@Getter
public class Asset extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Asset_ID")
    private Long id;

    private String type;

    private String link;

    private int isMain = 0;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product assetProduct;
}
