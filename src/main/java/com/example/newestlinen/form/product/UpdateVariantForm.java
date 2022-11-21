package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateVariantForm {
    @Schema(name = "variantId")
    private Long variantId;
    @Schema(name = "name")
    private String name;
    @Schema(name = "property")
    private String property;
    @Schema(name = "addPrice")
    private int addPrice;
    @Schema(name="status")
    private Integer status;
}
