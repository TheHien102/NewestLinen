package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdateProfileUserForm {
    @Schema(name = "username")
    @NotBlank(message = "username should not be empty")
    @NotEmpty(message = "username should not be empty")
    @Size(min = 6, max = 18, message = "username should between {min} and {max} character")
    private String username;

    @Schema(name = "password")
    @NotBlank(message = "password should not be empty")
    @NotEmpty(message = "password should not be empty")
    @Size(min = 8, max = 16, message = "password should between {min} and {max} character")
    private String password;

    @Schema(name = "oldPassword")
    private String oldPassword;

    @Schema(name = "fullName")
    @NotBlank(message = "fullName should not be empty")
    @NotEmpty(message = "fullName should not be empty")
    @Size(min = 1, max = 50, message = "fullName should between {min} and {max} character")
    private String fullName;

    @Schema(name = "avatar")
    private String avatar;

    @Email(message = "invalid email address")
    @Schema(name = "email")
    @NotBlank(message = "email should not be empty")
    @NotEmpty(message = "email should not be empty")
    @Size(min = 8, max = 50, message = "email should between {min} and {max} character")
    private String email;

    @Schema(name = "phone")
    @NotBlank(message = "phone should not be empty")
    @NotEmpty(message = "phone should not be empty")
    @Size(min = 10, max = 11, message = "phone should between {min} and {max} character")
    private String phone;
}
