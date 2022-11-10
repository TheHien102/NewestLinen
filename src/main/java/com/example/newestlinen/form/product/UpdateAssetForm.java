package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateAssetForm {
    @Schema(name = "assetId")
    private Long assetId;
    @Schema(name = "type")
    private String type;
    @Schema(name = "data")
    private String link;
    @Schema(name = "isMain")
    private int isMain;
    @Schema(name="status")
    private String status;
}
