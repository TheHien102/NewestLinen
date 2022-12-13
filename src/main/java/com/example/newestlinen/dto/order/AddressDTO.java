package com.example.newestlinen.dto.order;

import com.example.newestlinen.dto.cart.ProvinceDTO;
import com.example.newestlinen.storage.model.Address.Province;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    private ProvinceDTO city;

    private ProvinceDTO district;

    private ProvinceDTO ward;

    private String details;

    private String phone;
}
