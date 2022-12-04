package com.example.newestlinen.dto.product;

import com.example.newestlinen.storage.model.ProductModel.Review;
import lombok.Data;

import java.util.List;

@Data
public class ProductUserDTO {
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private int price;

    private String categoryDescription;
    
    private List<VariantDTO> variants;

    private List<Review> reviews;
}
