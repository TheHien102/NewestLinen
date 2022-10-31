package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class LoginForm {
    @NotEmpty(message = "username cant not be null")
    @Schema(name = "username", required = true)
    private String username;
    @NotEmpty(message = "password cant not be null")
    @Schema(name = "password", required = true)
    private String password;
}
