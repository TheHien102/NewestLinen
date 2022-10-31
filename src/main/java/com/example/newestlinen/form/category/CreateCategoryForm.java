package com.example.newestlinen.form.category;

import com.example.newestlinen.validation.CategoryKind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryForm {

    @NotEmpty(message = "categoryName cannot be null")
    @Schema(required = true)
    private String categoryName;

    @NotEmpty(message = "categoryDescription cannot be null")
    @Schema(required = true)
    private String categoryDescription;

    @Schema(required = true)
    private String categoryImage;

    @Schema(name = "categoryOrdering")
    private Integer categoryOrdering;

    @CategoryKind
    @NotNull(message = "categoryKind cannot be null")
    @Schema(required = true)
    private Integer categoryKind;

    @Schema(name = "parentId")
    private Long parentId;
}
