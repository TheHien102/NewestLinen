package com.example.newestlinen.form.account;

import com.example.newestlinen.validation.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class UpdateAccountAdminForm {

    @NotNull(message = "id cant not be null")
    @Schema(name = "id", required = true)
    private Long id;
    @Schema(name = "email")
    private String email;
    @Schema(name = "password")
    private String password;
    @NotEmpty(message = "fullName cant not be null")
    @Schema(name = "fullName", required = true)
    private String fullName;
    @Schema(name = "avatarPath")
    private String avatarPath;
    @Status
    private Integer status;
    @NotEmpty(message = "phone can not be empty")
    @Schema(name = "phone")
    private String phone;
}
