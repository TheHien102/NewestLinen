package com.example.newestlinen.form.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UploadAssetForm {
    @Schema(name = "type")
    private String type;
    @Schema(name = "data")
    private String data;
}
