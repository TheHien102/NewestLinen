package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Schema
public class UploadProductForm {

    @Schema(name = "name")
    @NotBlank(message = "name should not be empty")
    @NotNull(message = "name should not be empty")
    @NotEmpty(message = "name should not be empty")
    private String name;

    @Schema(name = "mainImg")
    @NotBlank(message = "mainImg should not be empty")
    @NotNull(message = "mainImg should not be empty")
    @NotEmpty(message = "mainImg should not be empty")
    private String mainImg;

    @Schema(name = "discount")
    @Min(value = 0,message = "price not zro")
    private int discount;

    @Schema(name = "description")
    @NotBlank(message = "description should not be empty")
    @NotNull(message = "description should not be empty")
    @NotEmpty(message = "description should not be empty")
    private String description;

    @Schema(name = "price")
    @Min(value = 1,message = "price not zro")
    @NotNull(message = "price should not be empty")
    private Integer price;

    @Schema(name = "productCategoryId")
    @Min(value = 1,message = "productCategoryId not zero")
    private Long productCategoryId;

    @Schema(name = "variants")
    @NotNull(message = "variants should not null")
    @Size(min = 1,message = "variant should not empty")
    private List<UploadVariantForm> variants;

    @Schema(name = "Assets")
    private List<UploadAssetForm> Assets;
}
