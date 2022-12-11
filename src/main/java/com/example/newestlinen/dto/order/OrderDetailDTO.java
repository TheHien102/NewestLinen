package com.example.newestlinen.dto.order;

import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.storage.model.CartModel.Item;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Data
public class OrderDetailDTO {
    private Long id;

    private ItemDTO item;

    private int quantity;

    private int discount;

    private int price;
}
