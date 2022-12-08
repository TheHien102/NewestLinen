package com.example.newestlinen.form.province;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class AddProvinceForm {
    @Schema(name = "name")
    @NotBlank
    @NotNull
    private String name;

    @Schema(name = "level")
    @NotBlank
    @NotNull
    @Min(value = 1)
    private int level;

    @Schema(name = "parentId")
    private Long parentId;
}
