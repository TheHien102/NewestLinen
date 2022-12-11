package com.example.newestlinen.dto.cart;

import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.dto.product.VariantDTO;
import com.example.newestlinen.storage.model.CartModel.Item;
import lombok.Data;

import javax.persistence.OneToOne;
import java.util.List;

@Data
public class CartItemDTO {
    private Long id;

    private Long productId;

    private List<VariantDTO> variants;

    private Integer quantity;

    private Integer price;

    private Integer discount;

    private String name;

    private String mainImg;
}
