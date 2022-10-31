package com.example.newestlinen.form.news.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class CreateGroupForm {
    @NotEmpty(message = "Name cant not be null")
    @Schema(name = "name", required = true)
    private String name;
    @NotEmpty(message = "description cant not be null")
    @Schema(name = "description", required = true)
    private String description;
    @NotNull(message = "permissions cant not be null")
    @Schema(name = "permissions", required = true)
    private Long[] permissions;
    @NotNull(message = "kind cant not be null")
    @Schema(name = "kind", required = true)
    private Integer kind;
}
