package com.example.newestlinen.dto.cart;

import lombok.Data;

@Data
public class ProvinceDTO {
    private Long id;
    private String name;
    private int level;
    private Long parentId;
}
