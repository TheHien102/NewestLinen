package com.example.newestlinen.form.order;

import com.example.newestlinen.dto.cart.ProvinceDTO;
import com.example.newestlinen.storage.model.Address.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema
public class CreateAddressForm {

    @NotNull
    @Schema(name = "province_cityId")
    private Long province_cityId;

    @NotNull
    @Schema(name = "province_districtId")
    private Long province_districtId;

    @NotNull
    @Schema(name = "province_wardId")
    private Long province_wardId;

    @NotNull
    @Schema(name = "details")
    private String details;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(name = "phone")
    private String phone;
}
