package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class UpdateProfileAdminForm {
    @Schema(name = "password")
    private String password;
    @NotEmpty(message = "oldPassword is required")
    @Schema(name = "oldPassword", required = true)
    private String oldPassword;
    @NotEmpty(message = "fullName is required")
    @Schema(name = "fullName", required = true)
    private String fullName;
    @Schema(name = "avatar")
    private String avatar;

}
