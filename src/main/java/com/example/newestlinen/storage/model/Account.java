package com.example.newestlinen.storage.model;

import com.example.newestlinen.storage.model.CartModel.Cart;
import com.example.newestlinen.storage.model.OrderModel.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.newestlinen.storage.model.Address.Address;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer kind;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @Column(name = "full_name")
    private String fullName;

    private String phone;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "reset_pwd_code")
    private String resetPwdCode;
    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;
    @Column(name = "attempt_forget_pwd")
    private Integer attemptCode;
    @Column(name = "attempt_login")
    private Integer attemptLogin;
    @Column(name = "verify_code")
    private String verifyCode;
    @Column(name = "verify_time")
    private Date verifyTime;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Cart_ID")
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addressUser = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orderUser = new ArrayList<>();
}
