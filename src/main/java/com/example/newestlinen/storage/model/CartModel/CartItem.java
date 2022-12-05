package com.example.newestlinen.storage.model.CartModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "CartItem")
@Getter
@Setter
@ToString
public class CartItem extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItem_ID")
    private Long id;

    @OneToOne
    private Item item;

    private int quantity;

    private int totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Cart_ID")
    private Cart cart;
}
