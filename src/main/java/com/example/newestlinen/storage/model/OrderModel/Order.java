package com.example.newestlinen.storage.model.OrderModel;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Order")
@Getter
@Setter
public class Order extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ID")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    private Long totalPrice;

    private Long shippingFee;

    private String address;

    private String phoneNumber;

    private String note;

    private Integer paymentType;

    @JsonIgnore
    @OneToOne
    private Account account;
}
