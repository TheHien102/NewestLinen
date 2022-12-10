package com.example.newestlinen.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;

    private String name;

    private String proImg;

    private List<VariantDTO> variants;
}
