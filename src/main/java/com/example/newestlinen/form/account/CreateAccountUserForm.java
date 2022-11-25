package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema
public class CreateAccountUserForm {
    @Schema(name = "username")
    private String username;

    @Email
    @Schema(name = "email")
    private String email;

    @Schema(name = "password")
    private String password;

    @Schema(name = "fullName")
    private String fullName;

    @Schema(name = "phone")
    private String phone;

    @Schema(name = "avatar")
    private String avatar;
}
