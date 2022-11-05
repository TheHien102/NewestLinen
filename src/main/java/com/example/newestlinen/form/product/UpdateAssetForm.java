package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateAssetForm {
    @Schema(name = "id")
    private Long id;
    @Schema(name = "type")
    private String type;
    @Schema(name = "data")
    private String data;
    @Schema(name = "isMain")
    private int isMain;
    @Schema(name="action")
    private String action;
}
