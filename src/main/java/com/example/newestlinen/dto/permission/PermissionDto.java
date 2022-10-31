package com.example.newestlinen.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PermissionDto {
    @Schema(name = "id")
    private Long id;
    @Schema(name = "name")
    private String name;
    @Schema(name = "action")
    private String action;
    @Schema(name = "showMenu")
    private Boolean showMenu;
    @Schema(name = "description")
    private String description;
    @Schema(name = "nameGroup")
    private String nameGroup;
    @Schema(name = "status")
    private Integer status;

}
