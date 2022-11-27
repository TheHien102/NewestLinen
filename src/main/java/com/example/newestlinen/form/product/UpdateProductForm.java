package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class UpdateProductForm {

    @Schema(name = "productId")
    private Long id;

    @Schema(name = "name")
    private String name;

    @Schema(name = "mainImg")
    private String mainImgNew;

    @Schema(name = "discount")
    private int discount;

    @Schema(name = "description")
    private String description;

    @Schema(name = "price")
    private int price;

    @Schema(name = "productCategoryID")
    private Long productCategoryID;

    @Schema(name = "variants")
    private List<UpdateVariantForm> variants;

    @Schema(name = "assets")
    private List<UpdateAssetForm> assets;
}
