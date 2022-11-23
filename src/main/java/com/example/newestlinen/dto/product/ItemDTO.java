package com.example.newestlinen.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;

    private String name;

    private ProductDetailDTO itemProduct;

    private List<VariantDTO> variants;
}
