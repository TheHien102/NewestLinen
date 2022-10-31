package com.example.newestlinen.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VerifyForm {
    @NotNull(message = "id can not be empty")
    @Schema(name = "id")
    private Long id;
    @NotEmpty(message = "otp can not be empty")
    @Schema(name = "otp")
    private String otp;
}
