package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UpdateProfileUserForm {
    @Schema(name = "username")
    @Size(min = 6, max = 18, message = "username should between {min} and {max} character")
    private String username;

    @Schema(name = "password")
    @Size(min = 8, max = 16, message = "password should between {min} and {max} character")
    private String password;

    @Schema(name = "oldPassword")
    private String oldPassword;

    @Schema(name = "fullName")
    @Size(min = 1, max = 50, message = "fullName should between {min} and {max} character")
    private String fullName;

    @Schema(name = "avatar")
    private String avatar;

    @Email(message = "invalid email address")
    @Schema(name = "email")
    @Size(min = 8, max = 50, message = "email should between {min} and {max} character")
    private String email;

    @Schema(name = "phone")
    @Pattern(regexp = "^07|9|8")
    @Size(min = 10, max = 11, message = "phone should between {min} and {max} character")
    private String phone;
}
