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
    @NotEmpty
    @Size(min = 6, max = 18)
    private String username;

    @Email(message = "invalid email address")
    @Schema(name = "email")
    @NotEmpty
    @Size(min = 8, max = 50, message = "email should between {min} and {max} character")
    private String email;

    @Schema(name = "password")
    @NotEmpty
    @Size(min = 8, max = 16, message = "password should between {min} and {max} character")
    private String password;

    @Schema(name = "fullName")
    @NotEmpty
    @Size(min = 1, max = 50, message = "fullName should between {min} and {max} character")
    private String fullName;

    @Schema(name = "phone")
    @NotEmpty
    @Size(min = 10, max = 11, message = "phone should between {min} and {max} character")
    private String phone;

    @Schema(name = "avatar")
    private String avatar;
}
