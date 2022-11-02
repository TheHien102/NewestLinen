package com.example.newestlinen.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Varriant")
@Getter
@Setter
public class Varriant extends Auditable<String> {
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
