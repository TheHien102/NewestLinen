package com.example.newestlinen.form.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class CreatePermissionForm {

    @NotEmpty(message = "name cant not be null")
    @Schema(name = "name", required = true)
    private String name;
    @NotEmpty(message = "action cant not be null")
    @Schema(name = "action", required = true)
    private String action;
    @NotNull(message = "showMenu cant not be null")
    @Schema(name = "showMenu", required = true)
    private Boolean showMenu;
    @NotEmpty(message = "description cant not be null")
    @Schema(name = "description", required = true)
    private String description;
    @NotEmpty(message = "nameGroup cant not be null")
    @Schema(name = "nameGroup", required = true)
    private String nameGroup;
}
