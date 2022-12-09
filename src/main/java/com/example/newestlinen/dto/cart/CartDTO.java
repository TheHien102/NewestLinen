package com.example.newestlinen.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long id;

    private List<CartItemDTO> cartItems;
}
