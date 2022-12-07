package com.example.newestlinen.form.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class AddProvinceForm {
    @Schema(name = "name")
    @NotBlank
    @NotNull
    private String name;

    @Schema(name = "parentId")
    private Long parentId;
}
