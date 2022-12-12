package com.example.newestlinen.storage.model.Address;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Auditable;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Address extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Address_ID")
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Province city;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Province district;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Province ward;

    private String phone;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;
}
