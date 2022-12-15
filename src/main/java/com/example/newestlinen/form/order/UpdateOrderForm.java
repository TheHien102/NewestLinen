package com.example.newestlinen.form.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema
@Data
public class UpdateOrderForm {
    @Schema(name = "id")
    @NotNull
    private Long id;

    @Schema(name = "status")
    @NotNull
    @Min(value = -2)
    @Max(value = 1)
    private Integer status;
}
