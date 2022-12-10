package com.example.newestlinen.form.account;

import com.example.newestlinen.validation.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@Schema
public class CreateAccountAdminForm {
    @NotEmpty(message = "username cant not be null")
    @Schema(name = "username", required = true)
    private String username;
    @Schema(name = "email")
    @Email
    private String email;
    @NotEmpty(message = "password cant not be null")
    @Schema(name = "password", required = true)
    private String password;
    @NotEmpty(message = "fullName cant not be null")
    @Schema(name = "fullName", required = true)
    private String fullName;
    private String avatarPath;

    @Status
    @NotNull(message = "status should not be null")
    private Integer status;

    @NotEmpty(message = "phone can not be empty")
    @Schema(name = "phone")
    private String phone;

    @Valid
    @NotNull(message = "groupId cannot be null")
    @Schema(name = "groupId", required = true)
    private Long groupId;
}
