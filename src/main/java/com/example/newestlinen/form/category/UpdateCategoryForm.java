package com.example.newestlinen.form.category;

import com.example.newestlinen.validation.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryForm {

    @NotNull(message = "id cannot be null")
    @Schema(required = true)
    private Long id;

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

    @Status
    @NotNull(message = "status cannot be null")
    @Schema(required = true)
    private Integer status;

}
