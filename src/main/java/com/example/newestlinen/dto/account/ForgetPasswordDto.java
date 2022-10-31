package com.example.newestlinen.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ForgetPasswordDto {
    @Schema(name = "idHash")
    private String idHash;
}
