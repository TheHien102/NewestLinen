package com.example.newestlinen.dto.product;

import lombok.Data;

@Data
public class VariantDTO {
    private Long id;
    private String name;
    private String property;
    private int addPrice;
}
