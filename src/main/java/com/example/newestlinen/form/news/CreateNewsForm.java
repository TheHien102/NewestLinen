package com.example.newestlinen.form.news;

import com.example.newestlinen.validation.CategoryKind;
import com.example.newestlinen.validation.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateNewsForm {

    @NotEmpty(message = "title cannot be null")
    @Schema(required = true)
    private String title;

    @NotEmpty(message = "content cannot be null")
    @Schema(required = true)
    private String content;

    @NotEmpty(message = "avatar cannot be null")
    @Schema(required = true)
    private String avatar;
    private String banner;

    @NotEmpty(message = "description cannot be null")
    @Schema(required = true)
    private String description;

    @NotNull(message = "categoryId cannot be null")
    @Schema(required = true)
    private Long categoryId;

    private Integer pinTop;

    @NotNull(message = "kind cannot be null")
    @Schema(required = true)
    @CategoryKind
    private Integer kind;

    @NotNull(message = "status cannot be null")
    @Schema(required = true)
    @Status
    private Integer status;
}