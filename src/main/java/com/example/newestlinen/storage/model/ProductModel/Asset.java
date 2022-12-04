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
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.TablePrefix;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "Asset")
@Setter
@Getter
@ToString
public class Asset extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Asset_ID")
    private Long id;

    private String type;

    private String link;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Product_ID")
    private Product assetProduct;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Asset) {
            Asset a = (Asset) obj;
            return Objects.equals(a.getId(), this.id);
        }
        return false;
    }
}
