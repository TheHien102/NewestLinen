package com.example.newestlinen.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class UploadFileForm {
    /**
     * Kieu upload la logo hay avatar.
     */
    @NotEmpty(message = "type is required")
    @Schema(name = "type", required = true)
    private String type ;
    @NotNull(message = "file is required")
    @Schema(name = "file", required = true)
    private MultipartFile file;
}
