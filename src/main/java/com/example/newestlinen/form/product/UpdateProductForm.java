package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema
public class UpdateProductForm {

    @Schema(name = "id")
    private Long id;

    @Schema(name = "name")
    private String name;

    @Schema(name = "mainImg")
    private String mainImg;

    @Schema(name = "discount")
    private int discount;

    @Schema(name = "description")
    private String description;

    @Schema(name = "price")
    private int price;

    @Schema(name = "productCategoryId")
    private Long productCategoryId;

    @Schema(name = "variants")
    private List<UpdateVariantForm> variants;

    @Schema(name = "assets")
    @NotNull(message = "assets should not null")
    private List<UpdateAssetForm> assets;
}
