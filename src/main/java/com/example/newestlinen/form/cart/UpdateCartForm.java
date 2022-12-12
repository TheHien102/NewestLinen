package com.example.newestlinen.form.cart;

import lombok.Data;

@Data
public class UpdateCartForm {
    private Long cartItemId;
    private int quantity;
}
