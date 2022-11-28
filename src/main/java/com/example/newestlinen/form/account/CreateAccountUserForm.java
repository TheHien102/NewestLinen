package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Schema
public class CreateAccountUserForm {
    @Schema(name = "username")
    @NotBlank(message = "username should not be empty")
    @NotEmpty(message = "username should not be empty")
    @Size(min = 6, max = 18,message = "username should between {min} and {max} character")
    private String username;

    @Email(message = "invalid email address")
    @Schema(name = "email")
    @NotBlank(message = "email should not be empty")
    @NotEmpty(message = "email should not be empty")
    @Size(min = 8, max = 50, message = "email should between {min} and {max} character")
    private String email;

    @Schema(name = "password")
    @NotBlank(message = "password should not be empty")
    @NotEmpty(message = "password should not be empty")
    @Size(min = 8, max = 16, message = "password should between {min} and {max} character")
    private String password;

    @Schema(name = "fullName")
    @NotBlank(message = "fullName should not be empty")
    @NotEmpty(message = "fullName should not be empty")
    @Size(min = 1, max = 50, message = "fullName should between {min} and {max} character")
    private String fullName;

    @Schema(name = "phone")
    @NotBlank(message = "phone should not be empty")
    @NotEmpty(message = "phone should not be empty")
    @Pattern(regexp = "^07|9|8")
    @Size(min = 10, max = 11, message = "phone should between {min} and {max} character")
    private String phone;

    @Schema(name = "avatar")
    private String avatar;
}
