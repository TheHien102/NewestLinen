package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema
public class UpdateProductForm {

    @Schema(name = "id")
    @NotBlank(message = "id should not be empty")
    @NotNull(message = "id should not be empty")
    @NotEmpty(message = "id should not be empty")
    private Long id;

    @Schema(name = "name")
    @NotBlank(message = "name should not be empty")
    @NotNull(message = "name should not be empty")
    @NotEmpty(message = "name should not be empty")
    private String name;

    @Schema(name = "mainImg")
    @NotBlank(message = "mainImg name should not be empty")
    @NotNull(message = "mainImg name should not be empty")
    @NotEmpty(message = "mainImg name should not be empty")
    private String mainImg;

    @Schema(name = "discount")
    @NotBlank(message = "discount should not be empty")
    @NotNull(message = "discount should not be empty")
    @NotEmpty(message = "discount should not be empty")
    private int discount;

    @Schema(name = "description")
    @NotBlank(message = "description should not be empty")
    @NotNull(message = "description should not be empty")
    @NotEmpty(message = "description should not be empty")
    private String description;

    @Schema(name = "price")
    @NotBlank(message = "price should not be empty")
    @NotNull(message = "price should not be empty")
    @NotEmpty(message = "price should not be empty")
    private Integer price;

    @Schema(name = "productCategoryId")
    @NotNull(message = "productCategoryId should not be empty")
    private Long productCategoryId;

    @Schema(name = "variants")
    @Size(min = 1,message = "variant should not be Empty")
    private List<UpdateVariantForm> variants;

    @Schema(name = "assets")
    private List<UpdateAssetForm> assets;
}
