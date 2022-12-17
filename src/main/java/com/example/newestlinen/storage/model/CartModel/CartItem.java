package com.example.newestlinen.storage.model.CartModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "CartItem")
@Getter
@Setter
public class CartItem extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItem_ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    private Integer quantity;

    private Integer discount;

    private Integer price;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;
}
