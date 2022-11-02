package com.example.newestlinen.dto.product;

import com.example.newestlinen.dto.ABasicAdminDto;
import com.example.newestlinen.storage.model.ProductModel.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewDTO extends ABasicAdminDto {
    private Long id;
    private int rate;
    private String comment;
    private Product reviewProduct;
}
