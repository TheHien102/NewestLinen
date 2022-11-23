package com.example.newestlinen.dto.product;

import com.example.newestlinen.dto.ABasicAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductAdminDTO extends ABasicAdminDto {
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private int price;

    private String categoryName;
}
