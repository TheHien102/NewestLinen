package com.example.newestlinen.form.order;

import com.example.newestlinen.storage.model.Address.Province;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAddressForm {

    @NotNull
    private Province city;

    @NotNull
    private Province district;

    @NotNull
    @NotBlank
    @NotEmpty
    private Province ward;

    @NotNull
    @NotBlank
    @NotEmpty
    private String phone;
}
