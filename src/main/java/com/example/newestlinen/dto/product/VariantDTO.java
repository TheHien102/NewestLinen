package com.example.newestlinen.dto.product;

import com.example.newestlinen.dto.ABasicAdminDto;
import com.example.newestlinen.storage.model.ProductModel.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class VariantDTO {
    private Long id;
    private String name;
    private String property;
    private int addPrice;
}
