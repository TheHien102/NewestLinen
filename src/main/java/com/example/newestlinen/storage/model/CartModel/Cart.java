package com.example.newestlinen.storage.model.CartModel;

import javax.persistence.*;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Cart")
@Getter
@Setter
public class Cart extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cart_ID")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @JsonIgnore
    @OneToOne
    private Account account;
}
