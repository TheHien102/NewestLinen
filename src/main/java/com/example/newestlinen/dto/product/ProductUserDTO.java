package com.example.newestlinen.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductUserDTO {
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private int price;

    private String categoryName;
    
    private List<VariantDTO> variants;
}
