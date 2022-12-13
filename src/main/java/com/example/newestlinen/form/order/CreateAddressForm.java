package com.example.newestlinen.form.order;

import com.example.newestlinen.dto.cart.ProvinceDTO;
import com.example.newestlinen.storage.model.Address.Province;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAddressForm {

    @NotNull
    private Long province_cityId;

    @NotNull
    private Long province_districtId;

    @NotNull
    private Long province_wardId;

    @NotNull
    private String details;

    @NotNull
    @NotBlank
    @NotEmpty
    private String phone;
}
