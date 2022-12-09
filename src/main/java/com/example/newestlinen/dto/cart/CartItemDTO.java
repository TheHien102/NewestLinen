package com.example.newestlinen.dto.cart;

import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.storage.model.CartModel.Item;
import lombok.Data;

import javax.persistence.OneToOne;

@Data
public class CartItemDTO {
    private Long id;

    private ItemDTO item;

    private int quantity;

    private int discount;

    private int price;

}
