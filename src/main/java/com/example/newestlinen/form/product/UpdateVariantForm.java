package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateVariantForm {
    @Schema(name = "id")
    private Long id;
    @Schema(name = "name")
    private String name;
    @Schema(name = "property")
    private String property;
    @Schema(name = "addPrice")
    private int addPrice;
    @Schema(name="action")
    private String status;
}
