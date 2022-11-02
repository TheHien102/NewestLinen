package com.example.newestlinen.storage.model.ProductModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.newestlinen.storage.model.Auditable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Varriant")
@Getter
@Setter
public class Variant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Varriant_ID")
    private Long id;

    private String name;

    private String property;

    private int addPrice;

    @ManyToOne
    @JoinColumn(name = "Item_ID", referencedColumnName = "Item_ID")
    private Item varriantItem;
}
