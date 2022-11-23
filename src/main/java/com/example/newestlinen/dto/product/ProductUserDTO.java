package com.example.newestlinen.dto.product;

import lombok.Data;

@Data
public class ProductUserDTO {
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private int price;

    private String categoryName;
}
