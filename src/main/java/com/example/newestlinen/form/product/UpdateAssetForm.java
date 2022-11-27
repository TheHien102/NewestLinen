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
    @Schema(name = "link")
    private String link;
}
