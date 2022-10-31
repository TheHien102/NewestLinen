package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class RequestForgetPasswordForm {

    @NotEmpty(message = "Email can not be null.")
    @Email
    @Schema(name = "email", required = true)
    private String email;
}
