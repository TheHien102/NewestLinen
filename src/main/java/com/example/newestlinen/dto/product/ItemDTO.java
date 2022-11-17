package com.example.newestlinen.dto.product;

import com.example.newestlinen.dto.ABasicAdminDto;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemDTO extends ABasicAdminDto {
    private Long itemId;

    private String name;

    private ProductDetailDTO itemProduct;

    private List<VariantDTO> variantList;
}
