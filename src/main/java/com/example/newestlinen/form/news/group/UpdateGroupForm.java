package com.example.newestlinen.form.news.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema
public class UpdateGroupForm {
    @NotNull(message = "id cant not be null")
    @Schema(name = "id", required = true)
    private Long id;
    @NotNull(message = "name cant not be null")
    @Schema(name = "name", required = true)
    private String name;
    @Schema(name = "description")
    private String description;
    @NotNull(message = "permissions cant not be null")
    @Schema(name = "permissions", required = true)
    private Long[] permissions;
}
