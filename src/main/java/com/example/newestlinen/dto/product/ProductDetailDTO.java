package com.example.newestlinen.dto.product;

import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Review;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDTO {
    private Long id;

    private String name;

    private String mainImg;

    private int discount;

    private String description;

    private int price;

    private String categoryDescription;

    private List<AssetDTO> assets;

    private List<VariantDTO> variants;

    private List<Review> reviews;
}
