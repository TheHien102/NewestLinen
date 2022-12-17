package com.example.newestlinen.storage.model.OrderModel;

import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "OrderDetail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetail_ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    private int quantity;

    private int discount;

    private int price;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Order_ID")
    private Order order;
}
