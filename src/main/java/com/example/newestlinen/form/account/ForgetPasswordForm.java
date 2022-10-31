package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema
public class ForgetPasswordForm {
    @NotEmpty(message = "OPT can not be null.")
    @Schema(name = "otp", required = true)
    private String otp;

    @NotEmpty(message = "Email can not be null.")
    @Schema(name = "idHash", required = true)
    private String idHash;

    @NotEmpty(message = "newPassword can not be null")
    @Size(min = 6, message = "newPassword minimum 6 character.")
    @Schema(name = "newPassword", required = true)
    private String newPassword;
}
